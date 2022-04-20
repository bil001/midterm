package learn.mastery.data;

import learn.mastery.models.Reservation;

import javax.xml.crypto.Data;
import java.util.List;

public interface ReservationRepository {

    List<Reservation> findById(String id);

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean delete(Reservation reservation) throws DataException;
}
