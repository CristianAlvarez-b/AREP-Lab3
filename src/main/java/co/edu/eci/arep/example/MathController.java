package co.edu.eci.arep.example;
import co.edu.eci.arep.appsvr.GetMapping;
import co.edu.eci.arep.appsvr.RestController;

@RestController
public class MathController {

    @GetMapping("/app/e")
    public static String e(String val) {
        return String.valueOf (Math.E);
    }

    @GetMapping("/app/pi")
    public static String pi(String val) {
        return String.valueOf (Math.PI);
    }
}
