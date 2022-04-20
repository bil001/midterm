package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.data.ReservationRepositoryDouble;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

import static learn.mastery.data.GuestRepositoryDouble.GUEST;
import static learn.mastery.data.HostRepositoryDouble.HOST;
import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new HostRepositoryDouble(),
            new GuestRepositoryDouble()
    );

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setResId(0);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(2));
        reservation.setHost(HOST);
        reservation.setGuest(GUEST);
        reservation.setTotal(BigDecimal.valueOf(200));

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
    }

    @Test
    void shouldNotAddNull() throws DataException{
        Result<Reservation> result = service.add(null);
        assertFalse(result.isSuccess());
        assertEquals("No reservation to save.",result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddNullHost() throws DataException{
//        Host is required.
        Reservation reservation = new Reservation();
        reservation.setResId(0);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(2));
        reservation.setHost(null);
        reservation.setGuest(GUEST);
        reservation.setTotal(BigDecimal.valueOf(200));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals("Host is required.",result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddNullGuest() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setResId(0);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(2));
        reservation.setHost(HOST);
        reservation.setGuest(null);
        reservation.setTotal(BigDecimal.valueOf(200));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals("Guest is required.",result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddNullDates() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setResId(0);
        reservation.setStartDate(null);
        reservation.setEndDate(null);
        reservation.setHost(HOST);
        reservation.setGuest(GUEST);
        reservation.setTotal(BigDecimal.valueOf(200));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals("Start date is required.",result.getErrorMessages().get(0));
        assertEquals("End date is required.",result.getErrorMessages().get(1));
    }

    @Test
    void shouldNotAddPastStart() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setResId(0);
        reservation.setStartDate(LocalDate.now().minusDays(2));
        reservation.setEndDate(LocalDate.now().plusDays(2));
        reservation.setHost(HOST);
        reservation.setGuest(GUEST);
        reservation.setTotal(BigDecimal.valueOf(200));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals("The start date must be in the future.",result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddOutOfSequence() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setResId(0);
        reservation.setStartDate(LocalDate.now().plusDays(30));
        reservation.setEndDate(LocalDate.now());
        reservation.setHost(HOST);
        reservation.setGuest(GUEST);
        reservation.setTotal(BigDecimal.valueOf(200));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals("The start date must be before the end date.",result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddNewHost() throws DataException{
        Host host = new Host();
        host.setId("Hello!");

        Reservation reservation = new Reservation();
        reservation.setResId(0);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(2));
        reservation.setHost(host);
        reservation.setGuest(GUEST);
        reservation.setTotal(BigDecimal.valueOf(200));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals("Host id not found.",result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddNewGuest() throws DataException{
        Guest guest = new Guest();
        guest.setId(257);

        Reservation reservation = new Reservation();
        reservation.setResId(0);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(2));
        reservation.setHost(HOST);
        reservation.setGuest(guest);
        reservation.setTotal(BigDecimal.valueOf(200));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals("Guest id not found.",result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddOverlap() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setResId(0);
        reservation.setStartDate(LocalDate.of(2024,4,19));
        reservation.setEndDate(LocalDate.of(2024,4,26));
        reservation.setHost(HOST);
        reservation.setGuest(GUEST);
        reservation.setTotal(BigDecimal.valueOf(200));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals("Reservation overlaps with an existing reservation.",result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddOverlapStart() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setResId(0);
        reservation.setStartDate(LocalDate.of(2024,4,21));
        reservation.setEndDate(LocalDate.of(2024,4,26));
        reservation.setHost(HOST);
        reservation.setGuest(GUEST);
        reservation.setTotal(BigDecimal.valueOf(200));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals("Start date overlaps with another reservation.",result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddSameStart() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setResId(0);
        reservation.setStartDate(LocalDate.of(2024,4,20));
        reservation.setEndDate(LocalDate.of(2024,4,26));
        reservation.setHost(HOST);
        reservation.setGuest(GUEST);
        reservation.setTotal(BigDecimal.valueOf(200));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertEquals("Start date overlaps with another reservation.",result.getErrorMessages().get(0));
    }
}