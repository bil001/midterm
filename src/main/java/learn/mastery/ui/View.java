package learn.mastery.ui;

import learn.mastery.models.State;

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

        String message = String.format("Select [%s-%s]",min,max);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
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
