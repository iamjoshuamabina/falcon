package org.falcon.claws;

import org.falcon.Config;

import java.io.File;

import static org.falcon.util.LogUtils.*;

public class ClawsTest {

	private static final String TAG = makeLogTag(ClawsTest.class);

	public void testHelp() {
		Claws.help();
	}

	public void testEla() {
		String imagePathString = System.getenv("HOME") + "/tmp/test_doc.jpg";
		String resultPathString = Config.ANALYSIS_DIR + "/test_doc_ela.png";

		Claws.ela(imagePathString);

		if( ! new File(resultPathString).isFile() ) {
			LOGE(TAG, "Ela result file was not found");
		} else {
			LOGF(TAG, "Ela completed successfully");
		}

	}

	public static void main(String[] args) {

		new ClawsTest().testHelp();
		new ClawsTest().testEla();
	}
}
