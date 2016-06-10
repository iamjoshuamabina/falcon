package org.falcon.claws;

import junit.framework.TestCase;
import org.falcon.Config;

public class ClawsTest extends TestCase
{

	public void setUp() throws Exception
	{
		super.setUp();

	}

	public void tearDown() throws Exception {}

	public void testCommandBuilder()
	{
		Claws claws = new Claws();
		String imagePath = "/home/joshy/tmp/usitupe_taka_hapa_doc.jpg";
		String expected = "claws -f " + imagePath + " -o "
				+ Config.ANALYSIS_DIR + " --json --ela --copymove";

		String actual = claws.commandBuilder(imagePath);

		assertEquals("CommandString: ", expected, actual);
	}
}
