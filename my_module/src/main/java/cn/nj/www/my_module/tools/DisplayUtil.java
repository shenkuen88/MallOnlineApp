package cn.nj.www.my_module.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * <dp、sp、px转换的工具类>
 * <功能详细描述>
 *
 * @author qiuqiaohua
 * @version [版本号, Apr 19, 2014]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DisplayUtil
{

    /**
     * <将px值转换为dip或dp值，保证尺寸大小不变>
     * <功能详细描述>
     *
     * @param context
     * @param pxValue DisplayMetrics类中属性density
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * <将dip或dp值转换为px值，保证尺寸大小不变>
     * <功能详细描述>
     *
     * @param context
     * @param dipValue DisplayMetrics类中属性density
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int dip2px(Context context, float dipValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * <将px值转换为sp值，保证文字大小不变>
     * <功能详细描述>
     *
     * @param context
     * @param pxValue DisplayMetrics类中属性scaledDensity
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int px2sp(Context context, float pxValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * <将sp值转换为px值，保证文字大小不变>
     * <功能详细描述>
     *
     * @param context
     * @param spValue DisplayMetrics类中属性scaledDensity
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int sp2px(Context context, float spValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * <获取屏幕像素>
     * <功能详细描述>
     *
     * @param activity
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int getScreenpx(Activity activity)
    {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * <获取屏幕像素密度比>
     * <功能详细描述>
     *
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static float getDensityRatio(Context context)
    {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        return density;
    }

    /**
     * <获取屏幕尺寸>
     * <功能详细描述>
     *
     * @param context
     * @return DisplayMetrics  displayMetrics.heightPixels 高度  displayMetrics.widthPixels 宽度
     * @see [类、类#方法、类#成员]
     */
    public static DisplayMetrics getScreenDisplay(Context context)
    {
        return context.getResources().getDisplayMetrics();
    }

    public static int getWidth(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getHeight(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int dpToPx(Resources res, int dp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                res.getDisplayMetrics());
    }

    /**
     * 获取sdk版本号
     */
    public static int getAndroidOSVersion()
    {
        int osVersion;
        try
        {
            osVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e)
        {
            osVersion = 0;
        }

        return osVersion;
    }

    /**
     * 判断手机是否存在虚拟键
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }
}
