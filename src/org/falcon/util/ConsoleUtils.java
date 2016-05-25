package org.falcon.util;

public class ConsoleUtils {

	private ConsoleUtils() {}

	public static String color(int i) {
		//noinspection StringConcatenationMissingWhitespace
		return (char) 27 + "[" + i + "m";
	}

	public static void out(String outputString) {
		System.out.println(outputString);
	}
}
