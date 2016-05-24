package org.falcon.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Console {

    private Console() {}

    public static void out(LogUtils level, String message) {
        LogUtils.log(level, message);
    }

    public static void out(String message) {
        LogUtils.log(LogUtils.INFO, message);
    }

    public static String in(String promptString) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        promptString += ": ";

        System.out.print(promptString);
        System.out.flush();

        try {
            return stdin.readLine().trim();
        } catch (Exception e) {
            Console.out(LogUtils.ERROR, "Console.in() -> " + e.getMessage());
        }
        return null;
    }

    public static String color(int i) {
        //noinspection StringConcatenationMissingWhitespace
        return getASCIIChar(27) + "[" + i + "m";
    }

    public static char getASCIIChar(int i) {
        return (char)i;
    }

}
