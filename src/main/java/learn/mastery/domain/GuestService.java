package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.models.Guest;

import java.util.List;

public class GuestService {

    private final GuestRepository repository;

    public GuestService(GuestRepository repository){this.repository=repository;}

    public List<Guest> findAll(){
        return repository.findAll();
    }

    public Result<Guest> add(Guest guest) throws DataException {
        Result<Guest> result = validate(guest);
        if(!result.isSuccess()){
            return result;
        }
        result.setPayload(repository.add(guest));
        return result;
    }

    private Result<Guest> validate(Guest guest){
        Result<Guest> result = validateNulls(guest);
        if(!result.isSuccess()){
            return result;
        }
        validateEmail(guest, result);
        if(!result.isSuccess()){
            return result;
        }
        List<Guest> all = repository.findAll();
        validateDuplicates(guest, all, result);
        return result;
    }

    private void validateEmail(Guest guest, Result<Guest> result){
        if(!guest.getEmail().contains("@") || !guest.getEmail().contains(".") || guest.getEmail().length() < 2){
            result.addErrorMessage("Invalid email.");
        }
    }

    private void validateDuplicates(Guest guest, List<Guest> all, Result<Guest> result){
        for(Guest g : all){
            if(g.getFirstName().equalsIgnoreCase(guest.getFirstName())
                && g.getLastName().equalsIgnoreCase(guest.getLastName())
                && g.getState().equalsIgnoreCase(guest.getState())){
                result.addErrorMessage("This guest is already registered.");
                return;
            }
        }
    }

    private Result<Guest> validateNulls(Guest guest){
        Result<Guest> result = new Result<>();
        if(guest == null){
            result.addErrorMessage("Guest is required.");
            return result;
        }if(guest.getFirstName() == null || guest.getFirstName().isEmpty()){
            result.addErrorMessage("Guest first name is required.");
        }if(guest.getLastName() == null || guest.getLastName().isEmpty()){
            result.addErrorMessage("Guest last name is required.");
        }if(guest.getEmail() == null || guest.getEmail().isEmpty()){
            result.addErrorMessage("Guest email is required.");
        }if(guest.getPhone() == null || guest.getPhone().isEmpty()){
            result.addErrorMessage("Guest phone is required.");
        }if(guest.getState() == null || guest.getState().isEmpty()){
            result.addErrorMessage("Guest state is required.");
        }
        return result;
    }
}
