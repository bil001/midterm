package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.HostRepository;
import learn.mastery.models.Host;

import java.math.BigDecimal;
import java.util.List;

public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository){this.repository = repository;}

    public List<Host> findAll(){
        return repository.findAll();
    }

    public Result<Host> add(Host host) throws DataException{
        Result<Host> result = validate(host);
        if(!result.isSuccess()){
            return result;
        }
        result.setPayload(repository.add(host));
        return result;
    }

    private Result<Host> validate(Host host){
        Result<Host> result = validateNulls(host);
        if(!result.isSuccess()){
            return result;
        }

        List<Host> all = repository.findAll();
        validateDuplicate(host, all, result);
        if(!result.isSuccess()){
            return result;
        }

        validateFees(host, result);
        return result;
    }

    private void validateDuplicate(Host host, List<Host> all, Result<Host> result){
        for(Host h : all){
            if(h.getId().equalsIgnoreCase(host.getId())){
                result.addErrorMessage("Unique id is required");
                return;
            }
            if(h.getState().equalsIgnoreCase(host.getState())
                && h.getCity().equalsIgnoreCase(host.getCity())
                && h.getPostalCode() == host.getPostalCode()
                && h.getAddress().equalsIgnoreCase(host.getAddress())){
                result.addErrorMessage("This property is already listed.");
                return;
            }
        }
    }

    private void validateFees(Host host, Result<Host> result){
        if(host.getWeekendRate().compareTo(BigDecimal.ZERO) < 0
            || host.getStandardRate().compareTo(BigDecimal.ZERO) < 0){
            result.addErrorMessage("Daily fee must be greater than zero");
        }
    }

    private Result<Host> validateNulls(Host host){
        Result<Host> result = new Result<>();
        if(host == null){
            result.addErrorMessage("Host is required.");
            return result;
        }
        if(host.getId() == null){
            result.addErrorMessage("Host id is required.");
        }
        if(host.getLastName() == null || host.getLastName().isEmpty()){
            result.addErrorMessage("Host last name is required");
        }
        if(host.getEmail() == null || host.getState().isEmpty()){
            result.addErrorMessage("Host state is required");
        }
        if(host.getPhone() == null || host.getPhone().isEmpty()){
            result.addErrorMessage("Host phone number is required");
        }
        if(host.getAddress() == null || host.getAddress().isEmpty()){
            result.addErrorMessage("Host address is required");
        }
        if(host.getCity() == null || host.getAddress().isEmpty()){
            result.addErrorMessage("Host city is required");
        }
        if(host.getStandardRate() == null){
            result.addErrorMessage("Standard fee is required");
        }
        if(host.getWeekendRate() == null){
            result.addErrorMessage("Weekend fee is required");
        }
        return result;
    }
}
