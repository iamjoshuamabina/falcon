package org.falcon.claws;

import static org.falcon.util.LogUtils.LOGE;
import static org.falcon.util.LogUtils.LOGF;
import static org.falcon.util.LogUtils.makeLogTag;

public class ClawsTest {

	private static final String TAG = makeLogTag(ClawsTest.class);

	public void testAnalyse() {
		String imagePathString = System.getenv("HOME") + "/tmp/test_doc.jpg";

		String analysisReport = Claws.analyse(imagePathString);

		if(!analysisReport.isEmpty()) LOGF(TAG, "Image analysis finished");
		else LOGE(TAG, "Image analysis failed");
	}

	public static void main(String[] args) {
		new ClawsTest().testAnalyse();
	}
}
