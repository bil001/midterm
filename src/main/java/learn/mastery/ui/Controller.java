package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.domain.Result;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.util.List;

public class Controller {

    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run(){
        view.displayHeader("Welcome to Don't Wreck My House!");
        try{
            runAppLoop();
        }catch(DataException ex){
            view.displayException(ex);
        }
        view.displayHeader("Goodbye...");
    }

    private void runAppLoop() throws DataException {
        MainMenuOption option;

        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW -> viewById();
                case ADD -> addReservation();
                case EDIT -> edit();
                case DELETE -> System.out.println("Feature not implemented yet");
            }
        } while (option != MainMenuOption.EXIT);
    }

    private void viewById(){
        view.displayHeader(MainMenuOption.VIEW.getMessage());
        List<Host> hosts = hostService.findAll();
        Host host = view.chooseHost(hosts);
        if(host!=null) {
            String hostId = host.getId();
            List<Reservation> reservations = reservationService.findById(hostId);
            view.displayReservations(reservations);
        }
        view.enterToContinue();
    }

    private void addReservation() throws DataException{
        view.displayHeader(MainMenuOption.ADD.getMessage());
        List<Host> hosts = hostService.findAll();
        Host host = view.chooseHost(hosts);
        if(host==null){
            return;
        }
        List<Guest> guests = guestService.findAll();
        Guest guest = view.chooseGuest(guests);
        if(guest==null){
            return;
        }
        Reservation reservation = view.makeReservation(host, guest);
        Result<Reservation> result = reservationService.add(reservation);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("+++++++++++++++++%n" +
                    "Reservation %s created.%n" +
                    "Start Date: %s%n" +
                    "End Date: %s%n" +
                    "Price: %s%n" +
                    "+++++++++++++++++",
                    result.getPayload().getResId(),
                    result.getPayload().getStartDate(),
                    result.getPayload().getEndDate(),
                    result.getPayload().getTotal());
            view.displayStatus(true, successMessage);
        }
    }

    private void edit() throws DataException{

    }
}
