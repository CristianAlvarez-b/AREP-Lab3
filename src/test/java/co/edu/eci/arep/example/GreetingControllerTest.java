package co.edu.eci.arep.example;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GreetingControllerTest {

    @Test
    void testGreetingWithName() {
        String result = GreetingController.greeting("Carlos");
        assertEquals("Hola Carlos", result, "Debe devolver 'Hola Carlos'");
    }

    @Test
    void testGreetingWithDefaultName() {
        String result = GreetingController.greeting("World");
        assertEquals("Hola World", result, "Debe devolver 'Hola World'");
    }
}
