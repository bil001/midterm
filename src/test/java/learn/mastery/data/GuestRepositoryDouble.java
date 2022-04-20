package learn.mastery.data;

import learn.mastery.models.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {
    //1,Sullivan,Lomas,slomas0@mediafire.com,(702) 7768761,NV

    public static final Guest GUEST = makeGuest();
    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble(){guests.add(GUEST);}

    private static Guest makeGuest(){
        Guest guest = new Guest();
        guest.setId(1);
        guest.setFirstName("Sullivan");
        guest.setLastName("Lomas");
        guest.setEmail("slomas0@mediafire.com");
        guest.setPhone("(702) 7768761");
        guest.setState("NV");
        return guest;
    }

    @Override
    public List<Guest> findAll() {
        return new ArrayList<>(guests);
    }
}
