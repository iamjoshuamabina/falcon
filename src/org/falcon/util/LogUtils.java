package org.falcon.util;

import org.falcon.Config;

public class LogUtils {
	private static final String LOG_PREFIX = "falcon_";

	public static String makeLogTag(Class cls) {
		return LOG_PREFIX + cls.getSimpleName();
	}

	private static String setUp(LoggerLevel loggerLevel) {
		return ConsoleUtils.color(loggerLevel.getLoggerValue())
				+ "_" + loggerLevel.getLoggerName() + ":\n";
	}

    public static void LOGE(final String tag, String message) {
		LOG(LoggerLevel.ERROR, message);
	}

	public static void LOGF(final String tag, String message) {
		LOG(LoggerLevel.FINE, message);
	}

	public static void LOGW(final String tag, String message) {
		LOG(LoggerLevel.WARNING, message);
	}

	public static void LOGI(final String tag, String message) {
		LOG(LoggerLevel.INFO, message);
	}

	public static void LOGD(final String tag, String message) {
		LOG(LoggerLevel.DEFAULT, message);
	}

	private static void LOG(LoggerLevel level, String message) {
		if(Config.SNAPSHOT_RELEASE) {
			ConsoleUtils.out(LogUtils.setUp(level) + message);
			LogUtils.LOG(LoggerLevel.DEFAULT);
		}
	}

	private static void LOG(LoggerLevel level) {
		ConsoleUtils.out(LogUtils.setUp(level));
    }

	private static class LoggerLevel {

        private String mName;
        private int mValue;

        public static final LoggerLevel ERROR = new LoggerLevel("ERROR", 31);
        public static final LoggerLevel FINE = new LoggerLevel("FINE", 32);
        public static final LoggerLevel WARNING = new LoggerLevel("WARNING", 33);
        public static final LoggerLevel INFO = new LoggerLevel("INFO", 34);
        public static final LoggerLevel DEFAULT = new LoggerLevel("DEFAULT", 38);

        public LoggerLevel(String name, int value) {
            this.mName = name;
            this.mValue = value;
        }

        private int getLoggerValue() {
            return this.mValue;
        }

        private String getLoggerName() {
            return this.mName;
        }

    }
}
