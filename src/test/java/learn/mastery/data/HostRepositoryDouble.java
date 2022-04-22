package learn.mastery.data;

import learn.mastery.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository{

    public final static Host HOST = makeHost();
    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble(){hosts.add(HOST);}

    private static Host makeHost(){
        Host host = new Host();
        host.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        host.setLastName("Yearnes");
        host.setEmail("eyearnes0@sfgate.com");
        host.setPhone("(806) 1783815");
        host.setAddress("3 Nova Trail");
        host.setCity("Amarillo");
        host.setState("TX");
        host.setPostalCode(79182);
        host.setStandardRate(BigDecimal.valueOf(340));
        host.setWeekendRate(BigDecimal.valueOf(425));
        return host;
    }

    @Override
    public List<Host> findAll() {
        return new ArrayList<>(hosts);
    }

    @Override
    public Host add(Host host) throws DataException {
        return null;
    }

    @Override
    public boolean update(Host host) throws DataException {
        return false;
    }

    @Override
    public boolean delete(Host host) throws DataException {
        return false;
    }
}
