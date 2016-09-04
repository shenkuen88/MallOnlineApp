package cn.nj.www.my_module.tools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.nj.www.my_module.constant.Global;

/**
 * <UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告>
 * <功能详细描述>
 *
 * @author wangtao
 */
public class CrashHandler implements UncaughtExceptionHandler
{
    public static final String TAG = "CrashHandler";

    //CrashHandler 实例
    private static CrashHandler INSTANCE = new CrashHandler();

    //context 
    private Context mContext;

    private UncaughtExceptionHandler mDefaultHandler;

    private Map<String, String> infos = new HashMap<String, String>();

    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private CrashHandler()
    {
    }

    ;

    public static CrashHandler getInstance()
    {
        return INSTANCE;
    }

    public void init(Context context)
    {
        mContext = context;
        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex)
    {
        if (!handleException(ex) && mDefaultHandler != null)
        {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else
        {
            try
            {
                thread.sleep(4000);
            } catch (InterruptedException e)
            {
                Log.e(TAG, "error:", e);
            }
            // 退出程序,注释下面的重启启动程序代码
            Global.logoutApplication();
//            Intent i = new Intent(mContext, TabIndicatorActivity.class);
            Intent i = new Intent("android.fbreader.action.CRASH",
                    new Uri.Builder().scheme(ex.getClass().getSimpleName())
                            .build());//            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }

    /**
     * <自定义错误处理，收集错误信息，发送错误报告等操作均在此完成>
     * <功能详细描述>
     *
     * @param ex
     * @return
     * @see [类、类#方法、类#成员]
     */
    private boolean handleException(Throwable ex)
    {
        if (ex == null)
        {
            return false;
        }
        //使用Toast 来显示异常信息
        new Thread()
        {
            @Override
            public void run()
            {
                Looper.prepare();
//                ToastUtil.makeText(mContext, "很抱歉，程序出现异常，即将退出");
                Looper.loop();
            }
        }.start();
        collectDeviceInfo(mContext);
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * <手机设备参数信息-收集设备信息>
     * <功能详细描述>
     *
     * @param ctx
     * @see [类、类#方法、类#成员]
     */
    public void collectDeviceInfo(Context ctx)
    {
        try
        {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null)
            {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e)
        {
            CMLog.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields)
        {
            try
            {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                CMLog.d(TAG, field.getName() + ":" + field.get(null));
            } catch (Exception e)
            {
                CMLog.e(TAG, "an error occured when collect crash info", e);
            }
        }

    }

    /**
     * <保存错误信息到文件中>
     * <功能详细描述>
     */
    private void saveCrashInfo2File(Throwable ex)
    {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null)
        {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try
        {
            String time = formatter.format(new Date());
            String fileName = FileSystemManager.getCrashPath(mContext) + time + ".log";
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (Exception e)
        {
            CMLog.e(TAG, "an error occured while writing file...", e);
        }
    }
}
