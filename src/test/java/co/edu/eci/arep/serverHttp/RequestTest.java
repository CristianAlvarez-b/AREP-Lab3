package co.edu.eci.arep.serverHttp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.StringReader;

public class RequestTest {

    @Test
    public void testExtractQueryParams() {
        BufferedReader mockReader = new BufferedReader(new StringReader(""));

        Request request = new Request("/app/hello?name=John&age=30", "GET", mockReader);

        assertEquals("John", request.getValues("name"));
        assertEquals("30", request.getValues("age"));
    }
    @Test
    public void testNoQueryParams() {
        BufferedReader mockReader = new BufferedReader(new StringReader(""));

        Request request = new Request("/app/hello", "GET", mockReader);

        assertNull(request.getValues("name"));
        assertNull(request.getValues("age"));
    }
    @Test
    public void testEmptyQueryParamValue() {
        BufferedReader mockReader = new BufferedReader(new StringReader(""));

        Request request = new Request("/app/hello?name=&age=30", "GET", mockReader);

        assertEquals(request.getValues("name"), "");
        assertEquals("30", request.getValues("age"));
    }
    @Test
    public void testMalformedQueryParams() {
        BufferedReader mockReader = new BufferedReader(new StringReader(""));

        Request request = new Request("/app/hello?name=&=30", "GET", mockReader);

        assertEquals(request.getValues("name"), "");
        assertNull(request.getValues("age"));
    }
    @Test
    public void testMethodAndNoQueryParams() {
        BufferedReader mockReader = new BufferedReader(new StringReader(""));

        Request request = new Request("/app/hello", "POST", mockReader);

        assertEquals("POST", request.getMethod());
        assertNull(request.getValues("name"));
    }
}