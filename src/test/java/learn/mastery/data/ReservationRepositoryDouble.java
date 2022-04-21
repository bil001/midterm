package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static learn.mastery.data.GuestRepositoryDouble.GUEST;
import static learn.mastery.data.HostRepositoryDouble.HOST;

public class ReservationRepositoryDouble implements ReservationRepository{

    final LocalDate startDate = LocalDate.of(2024,4,20);
    final LocalDate endDate = LocalDate.of(2024,4,25);

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble(){
        Reservation reservation = new Reservation();
        reservation.setResId(1);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setHost(HOST);
        reservation.setGuest(GUEST);
        reservation.setTotal(BigDecimal.valueOf(500));
        reservations.add(reservation);
    }

    @Override
    public List<Reservation> findById(String id) {
        return reservations.stream()
                .filter(reservation -> reservation.getHost().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        reservation.setResId(2);
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException{
        for(Reservation r : reservations){
            if(r.getResId() == reservation.getResId()){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {
        for(Reservation r : reservations){
            if(r.getResId() == reservation.getResId()){
                return true;
            }
        }
        return false;
    }

    @Override
    public BigDecimal findTotal(Reservation reservation) {
        return null;
    }
}
