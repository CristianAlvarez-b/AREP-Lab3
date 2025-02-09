package co.edu.eci.arep.serverHttp;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HttpServerTest {

    @Test
    public void testGetMimeTypeHtml() {
        String mimeType = HttpServer.getMimeType("/index.html");
        assertEquals("text/html", mimeType);
    }

    @Test
    public void testGetMimeTypeCss() {
        String mimeType = HttpServer.getMimeType("/style.css");
        assertEquals("text/css", mimeType);
    }

    @Test
    public void testGetMimeTypeJs() {
        assertEquals("application/javascript", HttpServer.getMimeType("/script.js"));
    }


    @Test
    public void testGetMimeTypeUnknown() {
        assertEquals("application/octet-stream", HttpServer.getMimeType("/file.unknown"));
    }

    @Test
    public void testStaticfiles() {
        HttpServer.staticfiles("webroot/public");

        assertEquals("target/classes/webroot/public", HttpServer.staticFilesLocation);
    }


    @Test
    public void testRouteNotFound() {
        Request mockRequest = mock(Request.class);
        Socket mockSocket = mock(Socket.class);
        when(mockRequest.getPath()).thenReturn("/nonexistent");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);

        HttpServer.handleStaticFileRequest("/nonexistent", out, mockSocket);

        String response = outputStream.toString();
        System.out.println(response);
        assertTrue(response.contains("HTTP/1.1 404 Not Found"));
        assertTrue(response.contains("<html><body><h1>404 Not Found</h1></body></html>"));
    }
}
