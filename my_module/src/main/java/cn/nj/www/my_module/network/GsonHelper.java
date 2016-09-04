package cn.nj.www.my_module.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import cn.nj.www.my_module.tools.CMLog;


public class GsonHelper
{
    //防止返回数据为null  为null时自动转换为空字符串
    private static Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();;

    /**
     * 把json string 转化成类对象
     * 
     * @param str
     * @param t
     * @return
     */
    public static <T> T toType(String str, Class<T> t)
    {
        try
        {
            if (str != null && !"".equals(str.trim()))
            {
                T res = gson.fromJson(str.trim(), t);
                return res;
            }
        }
        catch (Exception e)
        {
            CMLog.e("数据转换出错", "exception:" + e.getMessage());
        }
        return null;
    }
    
    /**
     * 把类对象转化成json string
     * 
     * @param t
     * @return
     */
    public static <T> String toJson(T t)
    {
        return gson.toJson(t);
    }

    /**
     * 把类json string转化成对象
     *
     * @param t
     * @return
     */
    public static <T> T fromJson(String t,Class<T> clas)
    {
        return gson.fromJson(t,clas);
    }
    /**
     * 把类json string转化成对象
     *
     * @param t
     * @return
     */
    public static <T> T fromJson(String t,Type clas)
    {
        return gson.fromJson(t,clas);
    }
}
