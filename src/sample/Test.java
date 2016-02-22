package sample;

import org.json.JSONObject;
import sample.util.Console;
import sample.util.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Test {

	public Test() {
	}

	public static String testCanGetGpsData() {
		String resultJsonString;
		Ghiro.GhiroBundle ghiroBundle;

		ArrayList<Ghiro.GhiroBundle> ghiroBundleList = new ArrayList<>();
		ghiroBundleList.add(new Ghiro.GhiroBundle("name", "GPS Test - " + Long.toString(System.currentTimeMillis())));
		ghiroBundleList.add(new Ghiro.GhiroBundle("description", "This is a description"));
		ghiroBundleList.add(new Ghiro.GhiroBundle("image", "/home/joshy/tmp/img/gpstestimg.jpg"));

		try {
			resultJsonString = Ghiro.analyse(Ghiro.Route.NEW_ANALYSIS, ghiroBundleList);

			JSONObject metadataJsonObject = new JSONObject(resultJsonString).getJSONObject("metadata");
			Console.out("Metadata: " + metadataJsonObject.toString());

			JSONObject gpsDataJsonObject = metadataJsonObject.getJSONObject("gps");
			Console.out("GPS: " + gpsDataJsonObject.toString());

			JSONObject posGpsDataJsonObject = gpsDataJsonObject.getJSONObject("pos");
			Console.out("GPS Position: " + posGpsDataJsonObject.toString());

			Console.out("GPS Altitude: " + String.valueOf(gpsDataJsonObject.get("Altitude")));
			Console.out("GPS Latitude: " + String.valueOf(posGpsDataJsonObject.get("Latitude")));
			Console.out("GPS Longitude: " + String.valueOf(posGpsDataJsonObject.get("Longitude")));

		} catch (IOException | URISyntaxException e) {
			Console.out(Logger.ERROR, e.getMessage());
		}

		return null;

	}

	public static void main(String[] args) {
		Console.out("Falcon Test");
		Test.testCanGetGpsData();
	}
}
