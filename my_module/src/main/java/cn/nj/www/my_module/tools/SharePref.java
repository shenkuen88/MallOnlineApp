package cn.nj.www.my_module.tools;

import android.content.Context;
import android.content.SharedPreferences;

import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.main.base.BaseApplication;


/**
 * 
 * <公共存储类>
 * <功能详细描述>
 * 
 * @author  cyf
 * @version  [版本号, 2014-11-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SharePref
{
    public static void saveBoolean(String key, boolean value)
    {
        getSharedPreferences().edit().putBoolean(key, value).commit();
    }
    
    public static boolean getBoolean(String key, boolean defvalue)
    {
        return getSharedPreferences().getBoolean(key, defvalue);
    }
    
    /**
     * Save a string value to the shared preference.
     * 
     * @param key
     *            to mark the store value.
     * @param value
     *            to saved value.
     */
    public static void saveString(String key, String value)
    {
        getSharedPreferences().edit().putString(key, value).commit();
    }
    
    /**
     * Get the specified value through the key value.
     * 
     * @param key
     *            to retrieve the value.
     * @return the string value returned.
     */
    public static String getString(String key, String def)
    {
        return getSharedPreferences().getString(key, def);
    }
    
    /**
     * Save a integer value to the shared preference.
     * 
     * @param key
     *            to mark the store value.
     * @param value
     *            to saved value.
     */
    public static void saveInt(String key, int value)
    {
        getSharedPreferences().edit().putInt(key, value).commit();
        
    }
    
    /**
     * Get the specified value through the key value.
     * 
     * @param key
     *            to retrieve the value.
     * @return the integer value returned.
     */
    public static int getInt(String key, int def)
    {
        return getSharedPreferences().getInt(key, def);
    }
    
    /**
     * Save a Long value to the shared preference.
     * 
     * @param key
     *            to mark the store value.
     * @param value
     *            to saved value.
     */
    public static void saveLong(String key, long value)
    {
        getSharedPreferences().edit().putLong(key, value).commit();
    }
    
    /**
     * Get the specified value through the key value.
     * 
     * @param key
     *            to retrieve the value.
     * @return the integer value returned.
     */
    public static long getLong(String key, long def)
    {
        return getSharedPreferences().getLong(key, def);
    }
    
    /**
     * Retrieve the package shared preferences object.
     * 
     * @param MyApp
     *            .appContext
     * @return
     */
    private static SharedPreferences getSharedPreferences()
    {
        return BaseApplication.getInstance().getSharedPreferences(Constants.BASE_PROJECT, Context.MODE_PRIVATE);
    }
}
