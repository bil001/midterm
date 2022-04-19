package learn.mastery.domain;

import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;

public class ReservationService {
    private final ReservationRepository reservationRepo;
    private final HostRepository hostRepo;
    private final GuestRepository guestRepo;

    public ReservationService(ReservationRepository reservationRepo, HostRepository hostRepo, GuestRepository guestRepo) {
        this.reservationRepo = reservationRepo;
        this.hostRepo = hostRepo;
        this.guestRepo = guestRepo;
    }
}
