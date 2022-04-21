package learn.mastery.ui;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import learn.mastery.models.State;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class View {
    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            io.printf("%s. %s%n", option.getValue(), option.getMessage());
            min = 0;
            max = option.getValue();
        }

        String message = String.format("Select [%s-%s]: ", min, max);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public Host chooseHost(List<Host> hosts) {
        displayHosts(hosts);

        if (hosts.size() == 0) {
            return null;
        }
        String email = io.readRequiredString("Select a host's email: ");

        Host host = hosts.stream()
                .filter(host1 -> host1.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
        if (host == null) {
            displayStatus(false, String.format("No host with email %s was found", email));
        }
        return host;
    }

    public Guest chooseGuest(List<Guest> guests) {
        displayGuests(guests);

        if (guests.size() == 0) {
            return null;
        }

        int id = io.readInt("Select a guest's id: ");

        Guest guest = guests.stream()
                .filter(guest1 -> guest1.getId() == id)
                .findFirst()
                .orElse(null);
        if (guest == null) {
            displayStatus(false, String.format("No guest with id [%s] was found", id));
        }
        return guest;
    }

    public Reservation makeReservation(Host host, Guest guest) {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStartDate(io.readLocalDate("Start Date [mm/dd/yyyy]: "));
        reservation.setEndDate(io.readLocalDate("End Date [mm/dd/yyyy]: "));
        return reservation;
    }

    public Reservation chooseReservation(List<Reservation> all) {
        int resId = io.readInt("Choose reservation id: ");
        Reservation res = all.stream()
                .filter(reservation -> reservation.getResId() == resId)
                .findFirst()
                .orElse(null);
        if (res == null) {
            displayStatus(false, String.format("No reservation with id [%s] was found", resId));
        }
        return res;
    }

    public List<Host> filterHostByState(List<Host> all, String stateAbb) {
        return all.stream()
                .filter(host -> host.getState().equalsIgnoreCase(stateAbb))
                .collect(Collectors.toList());
    }

    public List<Guest> filterGuestByState(List<Guest> all, String stateAbb) {
        return all.stream()
                .filter(guest -> guest.getState().equalsIgnoreCase(stateAbb))
                .collect(Collectors.toList());
    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    public void displayReservations(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }
        reservations = reservations.stream()
                .sorted(Comparator.comparing(Reservation::getStartDate))
                .toList();
        for (Reservation r : reservations) {
            io.printf("Id: [%s] | Guest: %s %s | Start Date: %s | End Date: %s | Revenue: %s%n",
                    r.getResId(),
                    r.getGuest().getFirstName(),
                    r.getGuest().getLastName(),
                    r.getStartDate(),
                    r.getEndDate(),
                    r.getTotal());
        }
    }

    public Reservation makeEditedDates() {
        displayHeader("Choose a new start and end date");
        Reservation reservation = new Reservation();
        reservation.setStartDate(io.readLocalDate("Start Date [mm/dd/yyyy]: "));
        reservation.setEndDate(io.readLocalDate("End Date [mm/dd/yyyy]: "));
        return reservation;
    }

    public boolean confirmEdit() {

        while (true) {
            String response = io.readRequiredString("Confirm? [y/n]: ");
            if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")) {
                return true;
            } else if (response.equalsIgnoreCase("n") || response.equalsIgnoreCase("no")){
                return false;
            }
            io.println("Invalid input.");
        }
    }

    public void displayReservationSummary(Reservation reservation) {
        displayHeader("Reservation");
        io.printf("Start Date: %s %nEnd Date: %s %nTotal Cost: $%s%n",
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getTotal().setScale(2, RoundingMode.HALF_UP));
    }

    public void displayGuests(List<Guest> guests) {
        if (guests.size() == 0) {
            io.println("No guests found.");
        }

        for (Guest g : guests) {
            io.printf("Name: %s %s | id: [%s] | Email: %s%n | State: %s",
                    g.getFirstName(),
                    g.getLastName(),
                    g.getId(),
                    g.getEmail(),
                    g.getState());
        }
    }

    public void displayHosts(List<Host> hosts) {
        if (hosts.size() == 0) {
            io.println("No hosts found.");
        }

        for (Host h : hosts) {
            io.printf("Last Name: %s | Email Address: %s | State: %s | Standard Rate: %s | Weekend Rate: %s%n",
                    h.getLastName(),
                    h.getEmail(),
                    h.getState(),
                    h.getStandardRate(),
                    h.getWeekendRate())
            ;
        }
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("+".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("An error has occurred");
        io.println(ex.getMessage());
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

    public String getState() {
        while (true) {
            String stateAbb = io.readRequiredString("Enter State Abbreviation: ");
            for (State s : State.values()) {
                if (stateAbb.equalsIgnoreCase(s.getAbbreviation())) {
                    return stateAbb;
                }
            }
            io.printf("No state \"%s\" found. Please enter a valid state abbreviation%n", stateAbb);
        }
    }
}
