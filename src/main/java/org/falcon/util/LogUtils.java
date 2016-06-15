package org.falcon.util;

import org.falcon.Config;

import java.util.Date;

public class LogUtils
{

	private static String setUp(LogLevel level)
	{
		if(level == LogLevel.DEFAULT)
			return level.getColor();

		return level.getColor() + new Date().toString()
				+ "\n"  + level.getName() + ": ";
	}

	private static void LOG(LogLevel level)
	{
		ConsoleUtils.out(LogUtils.setUp(level));
	}

	private static void LOG(LogLevel level, String message)
	{
		if(Config.SNAPSHOT_RELEASE) {
			ConsoleUtils.out(LogUtils.setUp(level) + message);
			LogUtils.LOG(LogLevel.DEFAULT);
		}
	}

	public static String makeLogTag(Class cls)
	{
		return cls.getName();
	}

	public static void LOGE(final String tag, String message)
	{
		LOG(LogLevel.ERROR, message);
	}

	public static void LOGF(final String tag, String message)
	{
		LOG(LogLevel.FINE, message);
	}

	public static void LOGW(final String tag, String message)
	{
		LOG(LogLevel.WARNING, message);
	}

	public static void LOGI(final String tag, String message)
	{
		LOG(LogLevel.INFO, message);
	}

	public static void LOGD(final String tag, String message)
	{
		LOG(LogLevel.DEFAULT, message);
	}

	private static class LogLevel
	{

		private String name;
		private int value;

		public static final LogLevel ERROR = new LogLevel("ERROR", 31);
		public static final LogLevel FINE = new LogLevel("FINE", 32);
		public static final LogLevel WARNING = new LogLevel("WARNING", 33);
		public static final LogLevel INFO = new LogLevel("INFO", 34);
		public static final LogLevel DEFAULT = new LogLevel("DEFAULT", 38);

		public LogLevel(String name, int value)
		{
			this.name = name;
			this.value = value;
		}

		public String getColor()
		{
			return ConsoleUtils.color(this.value);
		}

		public String getName()
		{
			return this.name;
		}

	}
}
