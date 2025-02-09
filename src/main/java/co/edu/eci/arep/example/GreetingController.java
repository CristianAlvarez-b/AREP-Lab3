package co.edu.eci.arep.example;

import co.edu.eci.arep.appsvr.GetMapping;
import co.edu.eci.arep.appsvr.RequestParam;
import co.edu.eci.arep.appsvr.RestController;

@RestController
public class GreetingController {


    @GetMapping("/app/greeting")
    public static String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hola " + name;
    }
}
