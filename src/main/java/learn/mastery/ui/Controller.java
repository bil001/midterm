package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;

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

    private void runAppLoop() throws DataException {
        MainMenuOption option;

        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW:
                    System.out.println("Feature not implemented yet");
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
}
