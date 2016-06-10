package org.falcon.util;

import static org.falcon.util.IOUtils.PWD;

public enum RouteUtils {
	ROOT("/"),
	ANALYSIS("analysis");

	private String mRouteName;

	RouteUtils(String route)
	{
		this.mRouteName = route;
	}

	public String toString()
	{
		return PWD(this.mRouteName);
	}

}
