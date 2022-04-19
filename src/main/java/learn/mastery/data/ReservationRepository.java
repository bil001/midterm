package learn.mastery.data;

import learn.mastery.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findById(String id);

    Reservation add(Reservation reservation) throws DataException;
}
