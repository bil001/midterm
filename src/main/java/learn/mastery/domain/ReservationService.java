package learn.mastery.domain;

import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

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
}
