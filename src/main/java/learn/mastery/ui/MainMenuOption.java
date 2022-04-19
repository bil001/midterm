package learn.mastery.ui;

public enum MainMenuOption {
    EXIT(0,"Exit"),
    VIEW(1,"View Reservations for Host"),
    ADD(2,"Make a Reservation"),
    EDIT(3,"Edit a Reservation"),
    DELETE(4,"Remove a Reservation");

    private int value;
    private String message;

    MainMenuOption(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static MainMenuOption fromValue(int value) {
        for(MainMenuOption option : MainMenuOption.values()){
            if(option.getValue() == value){
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {return value;}

    public String getMessage() {return message;}
}
