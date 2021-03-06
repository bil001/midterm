package learn.mastery.data;



import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {
    static final String TEST_FILE_PATH = "./data/reservations-test/3edda6bc-ab95-49a8-8962-d50b53f84b15.csv";
    static final String SEED_FILE_PATH = "./data/reservation-seed-3edda6bc-ab95-49a8-8962-d50b53f84b15.csv";
    static final String DIR_PATH = "./data/reservations-test";
    static final int RESERVATION_COUNT = 13;

    final String id = "3edda6bc-ab95-49a8-8962-d50b53f84b15";

    ReservationFileRepository repo = new ReservationFileRepository(DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindById() {
        List<Reservation> reservations = repo.findById(id);
        assertEquals(13,reservations.size());
    }

    @Test
    void shouldAdd() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2025,12,31));
        reservation.setEndDate(LocalDate.of(2026,1,4));
        reservation.setTotal(BigDecimal.valueOf(2000));

        Guest guest = new Guest();
        guest.setId(50);
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        host.setStandardRate(BigDecimal.valueOf(20));
        host.setWeekendRate(BigDecimal.valueOf(50));
        reservation.setHost(host);

        reservation = repo.add(reservation);

        assertEquals(14, reservation.getResId());
    }

    @Test
    void shouldCalculateTotal() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2022,4,20));
        reservation.setEndDate(LocalDate.of(2022,4,22));

        Guest guest = new Guest();
        guest.setId(50);
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        host.setStandardRate(BigDecimal.valueOf(100));
        host.setWeekendRate(BigDecimal.valueOf(150));
        reservation.setHost(host);

        reservation = repo.add(reservation);

        assertEquals(BigDecimal.valueOf(200), reservation.getTotal());
    }

    @Test
    void shouldUpdate() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setResId(12);
        reservation.setStartDate(LocalDate.of(3000,5,1));
        reservation.setEndDate(LocalDate.of(3000,6,7));

        Guest guest = new Guest();
        guest.setId(50);
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        reservation.setHost(host);
        host.setStandardRate(BigDecimal.valueOf(20));
        host.setWeekendRate(BigDecimal.valueOf(50));

        boolean result = repo.update(reservation);
        assertTrue(result);
    }

    @Test
    void shouldNotUpdate() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setResId(12);
        reservation.setStartDate(LocalDate.of(3000,5,1));
        reservation.setEndDate(LocalDate.of(3000,6,7));

        Guest guest = new Guest();
        guest.setId(50);
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId("Hello!");
        reservation.setHost(host);
        host.setStandardRate(BigDecimal.valueOf(20));
        host.setWeekendRate(BigDecimal.valueOf(50));

        boolean result = repo.update(reservation);
        assertFalse(result);
    }

    @Test
    void shouldDelete() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setResId(1);

        Host host = new Host();
        host.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        reservation.setHost(host);

        boolean result = repo.delete(reservation);
        assertTrue(result);
    }

    @Test
    void shouldNotDelete() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setResId(0);

        Host host = new Host();
        host.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        reservation.setHost(host);

        boolean result = repo.delete(reservation);
        assertFalse(result);
    }
}