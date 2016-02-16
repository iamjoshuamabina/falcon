package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by joshy on 4/25/15.
 *
 * A helper class implementing console methods
 *
 */
public class Console {

    public static void log(Logger level, String message) {
        Logger.log(level, message);
    }

    public static String input(String prompt) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        prompt += ": ";

        System.out.print(prompt);
        System.out.flush();

        try {
            return stdin.readLine().trim();
        } catch (Exception e) {
            return "Whoops! I guess " + e.getMessage() + " was experienced";
        }
    }

    public static String color(int i) {
        return getASCIIChar(27) + "[" + i + "m";
    }

    public static char getASCIIChar(int i) {
        return (char)i;
    }

}
