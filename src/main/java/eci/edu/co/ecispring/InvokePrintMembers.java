package eci.edu.co.ecispring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;

public class InvokePrintMembers {
    public static void main(String... args) {
        try {
            Class<?> c = Class.forName(args[0]);
            Class[] argTypes = new Class[]{Member[].class, "Pedro".getClass()};
            Method main = c.getDeclaredMethod("printMembers", argTypes);
            Class otraclase = Integer.class;
            Member[] members = otraclase.getMethods();
            System.out.format("invoking %s.main()%n", c.getName());
            main.invoke(null, (Object) members, "Methods");
            // production code should handle these exceptions more gracefully
        } catch (ClassNotFoundException x) {
            x.printStackTrace();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException x) {
            x.printStackTrace();
        }
    }
}
