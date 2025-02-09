package co.edu.eci.arep.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MathControllerTest {

    @Test
    void testE() {
        String result = MathController.e("");
        assertEquals(String.valueOf(Math.E), result);
    }

    @Test
    void testPi() {
        String result = MathController.pi("");
        assertEquals(String.valueOf(Math.PI), result);
    }
}
