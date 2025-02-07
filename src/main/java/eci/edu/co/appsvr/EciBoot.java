package eci.edu.co.appsvr;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EciBoot {
    public static Map<String, Method> services = new HashMap<>();
    public static void loadComponents(String[] args) {
        try {
            Class c = Class.forName(args[0]);
            if(!c.isAnnotationPresent(RestController.class)){
                System.exit(0);
            }
            for(Method m: c.getDeclaredMethods()){
                if(m.isAnnotationPresent(GetMapping.class)){
                    GetMapping a = m.getAnnotation(GetMapping.class);
                    services.put(a.value(),m);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        loadComponents(args);
        System.out.println(simulateRequest("/greeting"));
        System.out.println(simulateRequest("/pi"));
    }
    private static String simulateRequest(String route) throws InvocationTargetException, IllegalAccessException {
        Method s = services.get(route);
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                "{\"resp\":\"" + (String) s.invoke(null, "Pedro") + "\"}";

        return response;
    }
}
