package learn.mastery.data;

import learn.mastery.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {
    private static final String TEST_PATH = "./data/hosts-test.csv";
    private static final String SEED_PATH = "./data/hosts-seed.csv";

    HostFileRepository repo = new HostFileRepository(TEST_PATH);

    @BeforeEach
    public void setup() throws IOException{
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath,testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll(){
        List<Host> all = repo.findAll();
        assertEquals(1000,all.size());
    }

    @Test
    void shouldAdd() throws DataException {
        Host host = new Host();
        host.setLastName("Host");
        host.setEmail("email@gmail.com");
        host.setPhone("11123456");
        host.setAddress("Elm St.");
        host.setCity("Atlanta");
        host.setPostalCode(1234);
        host.setStandardRate(BigDecimal.ZERO);
        host.setWeekendRate(BigDecimal.ONE);
        host.setState("GA");
        repo.add(host);
        List<Host> all = repo.findAll();
        assertEquals(1001,all.size());
    }
}