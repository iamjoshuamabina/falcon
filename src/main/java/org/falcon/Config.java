package org.falcon;

public class Config {

	// Flag is set to true for unstable releases
	public static boolean SNAPSHOT_RELEASE = true;

	// Analysis temporary storage directory
	public static final String ANALYSIS_DIR = System.getenv("HOME") + "/.falcon/analysis";
}
