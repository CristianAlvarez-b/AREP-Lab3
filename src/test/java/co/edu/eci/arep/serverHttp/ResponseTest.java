package co.edu.eci.arep.serverHttp;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.*;

public class ResponseTest {

    @Test
    public void testSendFile() {
        PrintWriter mockPrintWriter = mock(PrintWriter.class);

        Socket mockSocket = mock(Socket.class);

        HttpServer mockHttpServer = mock(HttpServer.class);

        Response response = new Response(mockPrintWriter, mockSocket);

        String filePath = "/path/to/file.html";
        response.sendFile(filePath);

        verify(mockHttpServer).handleStaticFileRequest(filePath, mockPrintWriter, mockSocket);
    }

}

