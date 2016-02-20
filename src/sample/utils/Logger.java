package sample.utils;

public class Logger {

    @SuppressWarnings("FieldHasSetterButNoGetter")
    private String name;

    @SuppressWarnings("FieldHasSetterButNoGetter")
    private int value;

    private static boolean debugger = true;

    public static final Logger ERROR = new Logger("ERROR", 31);
    public static final Logger SUCCESS = new Logger("SUCCESS", 32);
    public static final Logger WARNING = new Logger("WARNING", 33);
    public static final Logger INFO = new Logger("INFO", 34);
    public static final Logger NORMAL = new Logger("NORMAL", 38);

    public Logger() {
    }

    public Logger(String var1, int value) {
        if(var1 == null) {
            throw new NullPointerException();
        } else {
            setName(var1);
            setValue(value);
        }
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final void setValue(int i) {
        this.value = i;
    }

    public static String setup(Logger mLogger) {
        return Console.color(mLogger.value) + mLogger.name;
    }

    public static String setup(Logger mLogger, boolean b) {
        if(b) return Console.color(mLogger.value);
        //noinspection ReturnOfNull
        return null;
    }

    public static void log(Logger level) {
        if(debugger) System.out.println(Logger.setup(level, true));
    }

    public static void log(Logger level, String message) {
        if(debugger) {
            System.out.println(Logger.setup(level) + " - " + message);
            Logger.log(Logger.NORMAL);
        }
    }

}
