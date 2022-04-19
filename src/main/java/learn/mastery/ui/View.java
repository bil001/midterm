package learn.mastery.ui;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import learn.mastery.models.State;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class View {
    private final ConsoleIO io;

    public View(ConsoleIO io) {this.io = io;}

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        for(MainMenuOption option : MainMenuOption.values()){
            io.printf("%s. %s%n", option.getValue(), option.getMessage());
            min=0;
            max=option.getValue();
        }

        String message = String.format("Select [%s-%s]: ",min,max);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public Host chooseHost(List<Host> hosts){
        displayHosts(hosts);

        if(hosts.size() == 0){
            return null;
        }
        String email = io.readRequiredString("Select a host's email: ");

        Host host = hosts.stream()
                .filter(host1 -> host1.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
        if(host == null){
            displayStatus(false, String.format("No host with email %s was found",email));
        }
        return host;
    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    public void displayReservations(List<Reservation> reservations){
        if (reservations == null || reservations.isEmpty()){
            io.println("No reservations found.");
            return;
        }
        reservations = reservations.stream()
                .sorted(Comparator.comparing(Reservation::getStarDate))
                .toList();
        for(Reservation r : reservations){
            io.printf("Guest: %s %s | Start Date: %s | End Date: %s | Revenue: %s%n",
                    r.getGuest().getFirstName(),
                    r.getGuest().getLastName(),
                    r.getStarDate(),
                    r.getEndDate(),
                    r.getTotal());
        }
    }

    public void displayHosts(List<Host> hosts){
        if(hosts.size()==0){
            io.println("No hosts found.");
        }

        for(Host h : hosts){
            io.printf("Last Name: %s | Email Address: %s%n",
                    h.getLastName(),
                    h.getEmail());
        }
    }

    public void displayHeader(String message){
        io.println("");
        io.println(message);
        io.println("+".repeat(message.length()));
    }

    public void displayException(Exception ex){
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

    public String getState(){
        while(true) {
            String stateAbb = io.readRequiredString("Enter State Abbreviation: ");
            for (State s : State.values()){
                if(stateAbb.equalsIgnoreCase(s.getAbbreviation())){
                    return stateAbb;
                }
            }
            io.printf("No state \"%s\" found. Please enter a valid state abbreviation%n",stateAbb);
        }
    }
}
