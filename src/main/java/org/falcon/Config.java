package org.falcon;

import java.awt.*;

public class Config {

	// Flag set to true for unstable releases.
	public static boolean SNAPSHOT_RELEASE = true;

	// In UNIX - USER_ROOT should read /home/chucknorris/Falcon.
	public static String USER_ROOT = "Falcon";

	// Encapsulates the bit depth, height, width, and refresh rate of the displaying device.
	public static final DisplayMode DISPLAY_MODE = GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getDefaultScreenDevice().getDisplayMode();

	// Width of the displaying device
	public static final double DISPLAY_MODE_WIDTH = DISPLAY_MODE.getWidth();

	// Height of the displaying device
	public static final double DISPLAY_MODE_HEIGHT = DISPLAY_MODE.getHeight();

}
