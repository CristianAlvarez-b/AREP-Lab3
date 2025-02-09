package co.edu.eci.arep.serverHttp;

import java.io.IOException;
import java.net.URISyntaxException;

import static co.edu.eci.arep.serverHttp.HttpServer.staticfiles;

public class WebApplication {
    public static void main(String[] args) throws IOException, URISyntaxException {
        staticfiles("webroot/public");
        HttpServer.start(args);
    }
}
