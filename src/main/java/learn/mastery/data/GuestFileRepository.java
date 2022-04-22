package learn.mastery.data;

import learn.mastery.models.Guest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GuestFileRepository implements GuestRepository {
    private final String filePath;
    private static final String HEADER = "guest_id,first_name,last_name,email,phone,state";

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAll() {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 6){
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {

        }
        return result;
    }

    @Override
    public Guest add(Guest guest) throws DataException{
        List<Guest> all =findAll();
        guest.setId(nextId(all));
        all.add(guest);
        writeAll(all);
        return guest;
    }

    @Override
    public boolean update(Guest guest) throws DataException{
        List<Guest> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getId() == guest.getId()){
                all.set(i,guest);
            }
        }
        return false;
    }

    @Override
    public boolean delete(Guest guest) throws DataException{
        List<Guest> all = findAll();
        for(Guest g : all){
            if(g.getId() == guest.getId()){
                all.remove(g);
                return true;
            }
        }
        return false;
    }

    private int nextId(List<Guest> all){
        return all.stream()
                .mapToInt(Guest::getId)
                .max()
                .orElse(0) + 1;
    }

    private String serialize(Guest guest) {
        return String.format("%s,%s,%s,%s,%s,%s",
                guest.getId(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getEmail(),
                guest.getPhone(),
                guest.getState());
    }

    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setId(Integer.parseInt(fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhone(fields[4]);
        result.setState(fields[5]);
        return result;
    }

    protected void writeAll(List<Guest> guests) throws DataException {
        try(PrintWriter writer = new PrintWriter(filePath)) {

            writer.println(HEADER);

            if(guests == null){
                return;
            }

            for(Guest g : guests){
                writer.println(serialize(g));
            }

        }catch (FileNotFoundException ex){
            throw new DataException(ex);
        }
    }
}
