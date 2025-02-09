package co.edu.eci.arep.serverHttp;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    private String path;
    private String method;
    private BufferedReader in;
    private Map<String, String> queryParams;
    private String body;

    public Request(String path, String method, BufferedReader in) {
        this.path = path.split("\\?")[0]; // Eliminar la parte de los parámetros en la ruta
        this.method = method;
        this.in = in;
        this.queryParams = new HashMap<>();
        extractQueryParams(path);
        extractBody();
    }

    private void extractQueryParams(String fullPath) {
        int queryIndex = fullPath.indexOf("?");
        if (queryIndex != -1 && queryIndex < fullPath.length() - 1) {
            String[] params = fullPath.substring(queryIndex + 1).split("&");
            for (String param : params) {
                String[] keyValue = param.split("=", 2);
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                } else {
                    queryParams.put(keyValue[0], ""); // Caso de parámetros sin valor
                }
            }
        }
    }

    private void extractBody() {
        try {
            if (in != null && "POST".equalsIgnoreCase(method)) {
                body = in.lines().collect(Collectors.joining("\n"));
            } else {
                body = "";
            }
        } catch (Exception e) {
            body = "";
        }
    }

    public String getQueryParam(String key) {
        return queryParams.getOrDefault(key, null);
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
    public String getValues(String key) {
        return queryParams.get(key);
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public BufferedReader getIn() {
        return in;
    }

    public String getBody() {
        return body;
    }
}
