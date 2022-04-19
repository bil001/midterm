package learn.mastery.domain;

import learn.mastery.data.HostRepository;
import learn.mastery.models.Host;

import java.util.List;

public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository){this.repository = repository;}

    public List<Host> findAll(){
        return repository.findAll();
    }
}
