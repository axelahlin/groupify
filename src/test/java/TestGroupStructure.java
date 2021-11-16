import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGroupStructure {
    Simulator s;

    @BeforeEach
    void setUp() {
        s = new Simulator(4);
    }

    @Test
    void demoTestMethod() {
        s.run("teams.txt", "seedingPts.txt");
        assertTrue(true);
    }
}
