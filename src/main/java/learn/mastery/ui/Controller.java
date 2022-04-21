package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.domain.Result;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.math.RoundingMode;
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
        Host host = selectHost();
        if(host!=null) {
            displayReservations(host);
        }
        view.enterToContinue();
    }

    private void addReservation() throws DataException{
        view.displayHeader(MainMenuOption.ADD.getMessage());
        Host host = selectHost();
        if(host==null){
            return;
        }
        Guest guest = selectGuest();
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
                    "Price: $%s%n" +
                    "+++++++++++++++++",
                    result.getPayload().getResId(),
                    result.getPayload().getStartDate(),
                    result.getPayload().getEndDate(),
                    result.getPayload().getTotal().setScale(2, RoundingMode.HALF_UP));
            view.displayStatus(true, successMessage);
        }
        view.enterToContinue();
    }

    private void edit() throws DataException{
        view.displayHeader(MainMenuOption.EDIT.getMessage());
        Host host = selectHost();
        List<Reservation> reservations = displayReservations(host);
        Reservation reservation = view.chooseReservation(reservations);
        Reservation editedDates = view.makeEditedDates();
        reservation.setTotal(reservationService.findTotal(editedDates));
        reservation.setStartDate(editedDates.getStartDate());
        reservation.setEndDate(editedDates.getEndDate());
        
        view.enterToContinue();
    }

    private Host selectHost(){
        List<Host> hosts = hostService.findAll();
        view.displayHeader("Host's state");
        String hostState = view.getState();
        hosts = view.filterHostByState(hosts,hostState);
        return view.chooseHost(hosts);
    }
    private Guest selectGuest(){
        List<Guest> guests = guestService.findAll();
        view.displayHeader("Guest's state");
        String guestState = view.getState();
        guests = view.filterGuestByState(guests,guestState);
        return view.chooseGuest(guests);
    }

    private List<Reservation> displayReservations(Host host) {
        String hostId = host.getId();
        List<Reservation> reservations = reservationService.findById(hostId);
        view.displayReservations(reservations);
        return reservations;
    }
}
