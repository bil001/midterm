package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {
    private final String directory;
    private static final String HEADER = "id,start_date,end_date,guest_id,total";

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    private String getFilePath(String id) {
        return Paths.get(directory, id + ".csv").toString();
    }

    @Override
    public List<Reservation> findById(String id) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(id)))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, id));
                }
            }
        } catch (IOException ex) {

        }
        return result;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> all = findById(reservation.getHost().getId());

        int nextId = all.stream()
                .mapToInt(Reservation::getResId)
                .max()
                .orElse(0) + 1;

        reservation.setTotal(findTotal(reservation));
        reservation.setResId(nextId);
        all.add(reservation);
        writeAll(all, reservation.getHost().getId());
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException{
        List<Reservation> all = findById(reservation.getHost().getId());
        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getResId()==reservation.getResId()){
                all.set(i, reservation);
                writeAll(all, reservation.getHost().getId());
                return true;
            }
        }
        return false;
    }

    private BigDecimal findTotal(Reservation reservation) {
        LocalDate start = reservation.getStartDate();
        LocalDate end = reservation.getEndDate();

        BigDecimal standardRate = reservation.getHost().getStandardRate();
        BigDecimal weekendRate = reservation.getHost().getWeekendRate();

        BigDecimal total = BigDecimal.ZERO;

        for (; !start.isEqual(end.plusDays(1)); start = start.plusDays(1)) {
            if(start.getDayOfWeek()== DayOfWeek.FRIDAY
                    || start.getDayOfWeek() == DayOfWeek.SATURDAY){
                total = total.add(weekendRate);
            }else{
                total = total.add(standardRate);
            }
        }

        return total;
    }

    private void writeAll(List<Reservation> reservations, String id) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(id))) {

            writer.println(HEADER);

            for (Reservation r : reservations) {
                writer.println(serialize(r));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getResId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuest().getId(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields, String id) {
        Reservation result = new Reservation();
        result.setResId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));
        result.setTotal(BigDecimal.valueOf(Double.parseDouble(fields[4])));

        Guest guest = new Guest();
        guest.setId(Integer.parseInt(fields[3]));
        result.setGuest(guest);

        Host host = new Host();
        host.setId(id);
        result.setHost(host);

        return result;
    }
}
