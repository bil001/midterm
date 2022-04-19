package learn.mastery.data;

import learn.mastery.models.Host;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostFileRepository implements HostRepository{
    private final String filePath;
    private static final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";

    public HostFileRepository(String filePath){this.filePath = filePath;}

    @Override
    public List<Host> findAll() {
        ArrayList<Host> result = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            reader.readLine();

            for(String line = reader.readLine(); line != null; line = reader.readLine()){

                String[] fields = line.split(",",-1);
                if(fields.length==10){
                    result.add(deserialize(fields));
                }
            }

        }catch(IOException ex){

        }
        return result;
    }

    private void writeAll (List<Host> hosts) throws DataException{
        try (PrintWriter writer = new PrintWriter(filePath)){
            writer.println(HEADER);

            for(Host h : hosts){
                writer.println(serialize(h));
            }

        }catch(IOException ex){
            throw new DataException(ex.getMessage(), ex);
        }
    }

    private String serialize(Host host){
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                host.getId(),
                host.getLastName(),
                host.getEmail(),
                host.getPhone(),
                host.getAddress(),
                host.getCity(),
                host.getState(),
                host.getPostalCode(),
                host.getStandardRate(),
                host.getWeekendRate());
    }

    private Host deserialize(String[] fields){
        Host result = new Host();
        result.setId(fields[0]);
        result.setLastName(fields[1]);
        result.setEmail(fields[2]);
        result.setPhone(fields[3]);
        result.setAddress(fields[4]);
        result.setCity(fields[5]);
        result.setState(fields[6]);
        result.setPostalCode(Integer.parseInt(fields[7]));
        result.setStandardRate(BigDecimal.valueOf(Double.parseDouble(fields[8])));
        result.setWeekendRate(BigDecimal.valueOf(Double.parseDouble(fields[9])));
        return result;
    }
}