package org.falcon.claws;

import org.falcon.Config;
import org.falcon.util.ConsoleUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.falcon.util.LogUtils.LOGE;
import static org.falcon.util.LogUtils.makeLogTag;

public class Claws {

	private static final String TAG = makeLogTag(Claws.class);

	public static void help() {
		String cmdString = "claws -h";

		Process process;
		//noinspection MismatchedQueryAndUpdateOfStringBuilder
		StringBuilder output = new StringBuilder();
		try {
			//noinspection CallToRuntimeExecWithNonConstantString
			process = Runtime.getRuntime().exec(cmdString);
			process.waitFor();
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine())!= null) {
				output.append(line).append("\n");
			}
			ConsoleUtils.out(output.toString());
		} catch (IOException | InterruptedException e) {
			LOGE(TAG, e.getMessage());
		}
	}

	public static void ela(String imagePathString) {
		String cmdString = "claws -f " + imagePathString + " -o " + Config.ANALYSIS_DIR + " --ela";

		Process process;
		//noinspection MismatchedQueryAndUpdateOfStringBuilder
		StringBuilder output = new StringBuilder();
		try {
			//noinspection CallToRuntimeExecWithNonConstantString
			process = Runtime.getRuntime().exec(cmdString);
			process.waitFor();
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine())!= null) {
				output.append(line).append("\n");
			}
			ConsoleUtils.out(output.toString());
		} catch (IOException | InterruptedException e) {
			LOGE(TAG, e.getMessage());
		}
	}
}
