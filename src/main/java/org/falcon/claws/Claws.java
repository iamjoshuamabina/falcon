package org.falcon.claws;

import org.falcon.Config;

public class Claws {

	public String commandBuilder(String imagePath) {
		return "claws -f " + imagePath +" -o " + Config.ANALYSIS_DIR
				+ " --json --ela --copymove";
	}

	public static String analyse(String imagePathString) {
		return null;
	}


}
