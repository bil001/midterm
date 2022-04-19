package learn.mastery.data;



import learn.mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {
    static final String TEST_FILE_PATH = "./data/3edda6bc-ab95-49a8-8962-d50b53f84b15.csv";
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
}