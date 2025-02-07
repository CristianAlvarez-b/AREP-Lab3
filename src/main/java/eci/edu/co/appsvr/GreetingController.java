package eci.edu.co.appsvr;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {


    @GetMapping("/greeting")
    public static String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hola " + name;
    }
    @GetMapping("/pi")
    public static String pi(String val) {
        return String.valueOf (Math.PI);
    }
}
