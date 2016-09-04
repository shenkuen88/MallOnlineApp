package cn.nj.www.my_module.tools;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * API for sending log output.
 * <p>
 * Generally, use the Log.v() Log.d() Log.i() Log.w() and Log.e() methods.
 * <p>
 * The order in terms of verbosity, from least to most is ERROR, WARN, INFO,
 * DEBUG, VERBOSE. Verbose should never be compiled into an application except
 * during development. Debug logs are compiled in but stripped at runtime.
 * Error, warning and info logs are always kept.
 * <p>
 * <b>Tip:</b> A good convention is to declare a <code>TAG</code> constant in
 * your class:
 * <p/>
 * <pre>
 * private static final String TAG = &quot;MyActivity&quot;;
 * </pre>
 * <p/>
 * and use that in subsequent calls to the log methods.
 * </p>
 * <p/>
 * <b>Tip:</b> Don't forget that when you make a call like
 * <p/>
 * <pre>
 * Log.v(TAG, &quot;index=&quot; + i);
 * </pre>
 * <p/>
 * that when you're building the string to pass into Log.d, the compiler uses a
 * StringBuilder and at least three allocations occur: the StringBuilder itself,
 * the buffer, and the String object. Realistically, there is also another
 * buffer allocation and copy, and even more pressure on the gc. That means that
 * if your log message is filtered out, you might be doing significant work and
 * incurring significant overhead.
 */
public final class CMLog
{
    private static final String TAG = CMLog.class.getName();

    /**
     * Define the log priority.
     */
    private static LogType logType;

    static
    {
        // TODO 发布的时候改成asset
//        setLogType(LogType.asset);
        setLogType(LogType.verbose);
    }

    private CMLog()
    {
    }

    /**
     * Get the log level.
     *
     * @return log level
     */
    public static LogType getLogType()
    {
        return logType;
    }

    /**
     * Set the log level for the application.
     *
     * @param logType the value to be set.
     */
    public static void setLogType(LogType logType)
    {
        CMLog.logType = logType;
        i(TAG, "logType: " + logType);
    }

    public static int v(String tag, String msg)
    {
        return CloudCentralPrintln(LogType.verbose.value(), tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr)
    {
        return CloudCentralPrintln(LogType.verbose.value(), tag, msg + '\n' + getStackTraceString(tr));
    }

    public static int d(String tag, String msg)
    {
        return CloudCentralPrintln(LogType.debug.value(), tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr)
    {
        return CloudCentralPrintln(LogType.debug.value(), tag, msg + '\n' + getStackTraceString(tr));
    }

    public static int i(String tag, String msg)
    {
        return CloudCentralPrintln(LogType.info.value(), tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr)
    {
        return CloudCentralPrintln(LogType.info.value(), tag, msg + '\n' + getStackTraceString(tr));
    }

    public static int w(String tag, String msg)
    {
        return CloudCentralPrintln(LogType.warn.value(), tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr)
    {
        return CloudCentralPrintln(LogType.warn.value(), tag, msg + '\n' + getStackTraceString(tr));
    }

    public static native boolean isLoggable(String tag, int level);

    /*
     * Send a {@link #WARN} log message and log the exception.
     * 
     * @param tag Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs.
     * 
     * @param tr An exception to log
     */
    public static int w(String tag, Throwable tr)
    {
        return CloudCentralPrintln(LogType.warn.value(), tag, getStackTraceString(tr));
    }

    public static int e(String tag, String msg)
    {
        return CloudCentralPrintln(LogType.error.value(), tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr)
    {
        return CloudCentralPrintln(LogType.error.value(), tag, msg + "\n" + getStackTraceString(tr));
    }

    private static String getStackTraceString(Throwable tr)
    {
        if (tr == null)
        {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return sw.toString();
    }

    private static int CloudCentralPrintln(int priority, String tag, String msg)
    {
        if (priority >= logType.value())
        {
            return android.util.Log.println(priority, tag, msg);
        } else
        {
            return -1;
        }
    }

    public static enum LogType
    {
        verbose(2), debug(3), info(4), warn(5), error(6), asset(7);

        private final int type;

        private LogType(int type)
        {
            this.type = type;
        }

        public int value()
        {
            return type;
        }

        public static LogType instanse(int i)
        {
            LogType type = verbose;
            switch (i)
            {
                case 2:
                    type = verbose;
                    break;
                case 3:
                    type = debug;
                    break;
                case 4:
                    type = info;
                    break;
                case 5:
                    type = warn;
                    break;
                case 6:
                    type = error;
                    break;
                case 7:
                    type = asset;
                    break;
                default:
                    type = null;
                    break;
            }
            return type;
        }
    }
}
