package sample;

import sample.util.Console;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Test {

	public Test() {
	}

	public static String canGetGpsData() {
		String resultJsonString;
		Ghiro.GhiroBundle ghiroBundle;

		ArrayList<Ghiro.GhiroBundle> ghiroBundleList = new ArrayList<>();
		ghiroBundleList.add(new Ghiro.GhiroBundle("name", "GPS Test - " + Long.toString(System.currentTimeMillis())));
		ghiroBundleList.add(new Ghiro.GhiroBundle("description", "This is a description"));
		ghiroBundleList.add(new Ghiro.GhiroBundle("image", "/home/joshy/tmp/img/gpstestimg.jpg"));

		try {
			resultJsonString = Ghiro.analyse(Ghiro.Route.NEW_ANALYSIS, ghiroBundleList);
			Console.out(resultJsonString);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static void main(String[] args) {
		Console.out("Falcon Test");
		Test.canGetGpsData();
	}
}
