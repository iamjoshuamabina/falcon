package org.falcon.claws;

import org.falcon.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.falcon.util.LogUtils.LOGE;
import static org.falcon.util.LogUtils.LOGI;
import static org.falcon.util.LogUtils.makeLogTag;

public class Claws {

	private static final String TAG = makeLogTag(Claws.class);

	public static String analyse(String imagePathString) {
		String cmdString = "claws -f " + imagePathString
				+ " -o " + Config.ANALYSIS_DIR
				+ " --ela "
				+ "--copymove 4 5 "
				+ "--json";

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
			LOGI(TAG, String.valueOf(output));
		} catch (IOException | InterruptedException e) {
			LOGE(TAG, e.getMessage());
		}
		return String.valueOf(output);
	}
}
