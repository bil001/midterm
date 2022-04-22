package learn.mastery.data;

import learn.mastery.models.Host;

import java.util.List;

public interface HostRepository {
    List<Host> findAll();

    Host add(Host host) throws DataException;

    boolean update(Host host) throws DataException;

    boolean delete(Host host) throws DataException;
}
