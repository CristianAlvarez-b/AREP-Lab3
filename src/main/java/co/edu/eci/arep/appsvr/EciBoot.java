package co.edu.eci.arep.appsvr;

import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class EciBoot {

    static final Map<String, Method> services = new HashMap<>();
    static final Map<String, Object> controllers = new HashMap<>();

    public static void loadComponents() {
        try {
            // Explorar el classpath en busca de clases anotadas con @RestController
            for (Class<?> clazz : findAllClasses()) {
                if (clazz.isAnnotationPresent(RestController.class)) {
                    System.out.println("RestController found: " + clazz.getName());
                    Object controllerInstance = clazz.getDeclaredConstructor().newInstance();
                    for (Method method : clazz.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(GetMapping.class)) {
                            GetMapping mapping = method.getAnnotation(GetMapping.class);
                            System.out.println("Registering route: " + mapping.value());
                            services.put(mapping.value(), method);
                            controllers.put(mapping.value(), controllerInstance);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading components", e);
        }
    }

    public static String handleRequest(String path, Map<String, String> queryParams) {

        try {
            Method method = services.get(path);
            if (method == null) {
                return "HTTP/1.1 404 Not Found\r\nContent-Type: application/json\r\n\r\n{\"error\":\"Route not found\"}";
            }

            Object controller = controllers.get(path);
            Object[] args = new Object[method.getParameterCount()];
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].isAnnotationPresent(RequestParam.class)) {
                    RequestParam param = parameters[i].getAnnotation(RequestParam.class);
                    args[i] = queryParams.getOrDefault(param.value(), param.defaultValue());
                }
            }

            String response = (String) method.invoke(controller, args);
            return "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n{\"resp\":\"" + response + "\"}";
        } catch (Exception e) {
            return "HTTP/1.1 500 Internal Server Error\r\nContent-Type: application/json\r\n\r\n{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private static Iterable<Class<?>> findAllClasses() {
        Reflections reflections = new Reflections("co.edu.eci.arep");
        return reflections.getTypesAnnotatedWith(RestController.class);
    }
}
