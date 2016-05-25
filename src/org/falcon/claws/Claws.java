package org.falcon.claws;

import org.falcon.util.ConsoleUtils;
import org.falcon.util.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Claws {

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
			ConsoleUtils.out(LogUtils.ERROR, e.getMessage());
		}
	}
}
