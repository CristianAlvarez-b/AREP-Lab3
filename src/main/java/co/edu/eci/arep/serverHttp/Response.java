package co.edu.eci.arep.serverHttp;
import java.io.PrintWriter;
import java.net.Socket;
public class Response {
    private PrintWriter out;
    private Socket clientSocket;

    public Response(PrintWriter out, Socket clientSocket) {
        this.out = out;
        this.clientSocket = clientSocket;
    }

    public void send(String response) {
        out.print(response);
        out.flush();
    }

    public void sendFile(String filePath) {
        HttpServer.handleStaticFileRequest(filePath, out, clientSocket);
    }
}