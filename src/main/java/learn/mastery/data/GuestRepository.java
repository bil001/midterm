package learn.mastery.data;

import learn.mastery.models.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll();

    Guest add(Guest guest) throws DataException;

    boolean update(Guest guest) throws DataException;

    boolean delete(Guest guest) throws DataException;
}
