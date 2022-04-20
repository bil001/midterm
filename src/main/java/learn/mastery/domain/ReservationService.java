package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationService {
    private final ReservationRepository reservationRepo;
    private final HostRepository hostRepo;
    private final GuestRepository guestRepo;

    public ReservationService(ReservationRepository reservationRepo, HostRepository hostRepo, GuestRepository guestRepo) {
        this.reservationRepo = reservationRepo;
        this.hostRepo = hostRepo;
        this.guestRepo = guestRepo;
    }

    public List<Reservation> findById(String id){
        Map<Integer, Guest> guestMap = guestRepo.findAll().stream()
                .collect(Collectors.toMap(Guest::getId, guest -> guest));
        Map<String, Host> hostMap = hostRepo.findAll().stream()
                .collect(Collectors.toMap(Host::getId, host -> host));

        List<Reservation>  result = reservationRepo.findById(id);
        for(Reservation r : result){
            r.setHost(hostMap.get(id));
            r.setGuest(guestMap.get(r.getGuest().getId()));
        }

        return result;
    }

    public Result<Reservation> add(Reservation reservation) throws DataException{
        Result<Reservation> result = validate(reservation);
        if(!result.isSuccess()){
            return result;
        }

        result.setPayload(reservationRepo.add(reservation));

        return result;
    }

    private Result<Reservation> validate(Reservation reservation){
        Result<Reservation> result = validateNulls(reservation);
        if(!result.isSuccess()){
            return result;
        }

        validateFields(reservation,result);
        if(!result.isSuccess()){
            return result;
        }

        List<Guest> guests = guestRepo.findAll();
        validateGuestExists(guests, reservation, result);
        if(!result.isSuccess()){
            return result;
        }

        List<Host> hosts = hostRepo.findAll();
        validateHostExists(hosts,reservation,result);
        if(!result.isSuccess()){
            return result;
        }

        List<Reservation> reservations = reservationRepo.findById(reservation.getHost().getId());
        validateOverlap(reservations, reservation, result);

        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation){
        Result<Reservation> result = new Result<>();

        if (reservation == null){
            result.addErrorMessage("No reservation to save.");
            return result;
        }

        if(reservation.getHost() == null){
            result.addErrorMessage("Host is required.");
        }

        if(reservation.getGuest() == null){
            result.addErrorMessage("Guest is required.");
        }

        if(reservation.getStartDate() == null){
            result.addErrorMessage("Start date is required.");
        }

        if(reservation.getEndDate() == null){
            result.addErrorMessage("End date is required.");
        }
        return result;
    }

    private void validateHostExists(List<Host> hosts, Reservation reservation, Result<Reservation> result){
        for(Host h : hosts){
            if(h.getId().equalsIgnoreCase(reservation.getHost().getId())){
                return;
            }
        }
        result.addErrorMessage("Host id not found.");
    }

    private void validateGuestExists(List<Guest> guests, Reservation reservation, Result<Reservation> result){
        for(Guest g : guests){
            if(g.getId() == reservation.getGuest().getId()){
                return;
            }
        }
        result.addErrorMessage("Guest id not found.");
    }

    private void validateFields(Reservation reservation, Result<Reservation> result){

        if(reservation.getStartDate().isAfter(reservation.getEndDate())){
            result.addErrorMessage("The start date must be before the end date.");
        }

        if(reservation.getStartDate().isBefore(LocalDate.now())){
            result.addErrorMessage("The start date must be in the future.");
        }

    }
    //TODO reverse date validations
    private void validateOverlap(List<Reservation> all, Reservation reservation, Result<Reservation> result){
        for (Reservation r : all){
            if((reservation.getStartDate().isAfter(r.getStartDate())
                    && reservation.getStartDate().isBefore(r.getEndDate()))
                    || reservation.getStartDate().isEqual(r.getStartDate())){
                result.addErrorMessage("Start date overlaps with another reservation.");
            }
            if((reservation.getEndDate().isAfter(r.getStartDate())
                    && reservation.getEndDate().isBefore(r.getEndDate()))
                    || reservation.getEndDate().isEqual(r.getEndDate())){
                result.addErrorMessage("End date overlaps with another reservation.");
            }
            if(reservation.getStartDate().isBefore(r.getStartDate())
                    && reservation.getEndDate().isAfter(r.getEndDate())){
                result.addErrorMessage("Reservation overlaps with an existing reservation.");
            }
//            if(reservation.getStartDate().isEqual())
        }
    }
}
