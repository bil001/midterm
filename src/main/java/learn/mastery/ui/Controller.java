package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
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
                case VIEW:
                    viewById();
                    break;
                case ADD:
                    System.out.println("Feature not implemented yet");
                    break;
                case EDIT:
                    System.out.println("Feature not implemented yet");
                    break;
                case DELETE:
                    System.out.println("Feature not implemented yet");
                    break;
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
}
