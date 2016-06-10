package org.falcon;

import org.falcon.util.RouteUtils;
import org.junit.Test;

import static java.lang.System.getProperty;
import static org.junit.Assert.assertEquals;

public class ConfigTest {

	@Test
	public void userRootIsValid()
	{
		String expected = getProperty("user.home") + "/Falcon";

		String actual = RouteUtils.ROOT.toString();

		assertEquals(expected, actual);
	}

}
