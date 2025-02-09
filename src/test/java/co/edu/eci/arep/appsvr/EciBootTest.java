package co.edu.eci.arep.appsvr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EciBootTest {

    @Mock
    private TestController testController; // Mock de un controlador de prueba

    private Method testMethod;

    @BeforeEach
    void setUp() throws Exception {
        // Simulamos un método en un controlador de prueba con la anotación @GetMapping
        testMethod = TestController.class.getDeclaredMethod("hello", String.class);

        // Registramos manualmente el método en el framework (simulando loadComponents())
        EciBoot.services.put("/test/hello", testMethod);
        EciBoot.controllers.put("/test/hello", testController);
    }

    @Test
    void testHandleRequestWithValidRoute() throws Exception {
        // Simula el comportamiento del método del controlador
        when(testController.hello("World")).thenReturn("Hello World");

        // Ejecuta handleRequest con un parámetro
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "World");
        String response = EciBoot.handleRequest("/test/hello", queryParams);

        // Verifica que la respuesta es correcta
        assertTrue(response.contains("HTTP/1.1 200 OK"));
        assertTrue(response.contains("{\"resp\":\"Hello World\"}"));
    }

    @Test
    void testHandleRequestWithMissingParam_UsesDefaultValue() throws Exception {
        // Simula el método con un valor por defecto
        when(testController.hello("Default")).thenReturn("Hello Default");

        // Ejecuta handleRequest sin pasar el parámetro
        Map<String, String> queryParams = new HashMap<>();
        String response = EciBoot.handleRequest("/test/hello", queryParams);

        // Verifica que usa el valor por defecto
        assertTrue(response.contains("{\"resp\":\"Hello Default\"}"));
    }

    @Test
    void testHandleRequestWithInvalidRoute() {
        // Prueba una ruta que no existe
        String response = EciBoot.handleRequest("/invalid/route", new HashMap<>());

        // Verifica que devuelve un error 404
        assertTrue(response.contains("HTTP/1.1 404 Not Found"));
        assertTrue(response.contains("{\"error\":\"Route not found\"}"));
    }

    @Test
    void testHandleRequestWithExceptionHandling() throws Exception {
        // Simula un error en el método del controlador
        when(testController.hello(anyString())).thenThrow(new RuntimeException("Test Exception"));

        // Prueba la llamada
        String response = EciBoot.handleRequest("/test/hello", Map.of("name", "Error"));

        // Verifica que devuelve un error 500
        assertTrue(response.contains("HTTP/1.1 500 Internal Server Error"));
    }

    // Clase de prueba que simula un controlador con una anotación RestController
    @RestController
    static class TestController {
        @GetMapping("/test/hello")
        public String hello(@RequestParam(value = "name", defaultValue = "Default") String name) {
            return "Hello " + name;
        }
    }
}
