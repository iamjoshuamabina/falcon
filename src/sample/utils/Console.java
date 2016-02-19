package sample.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Console {

    public Console() {
    }

    public static void out(Logger level, String message) {
        Logger.log(level, message);
    }

    public static String in(String prompt) {
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

    // TODO: A better way to get ASCII chars
    public static String color(int i) {
        return getASCIIChar(27) + "[" + i + "m";
    }

    public static char getASCIIChar(int i) {
        return (char)i;
    }

}
