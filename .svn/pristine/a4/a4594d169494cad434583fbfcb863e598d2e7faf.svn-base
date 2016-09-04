package cn.nj.www.my_module.tools;

import android.content.Context;
import android.widget.Toast;

import cn.nj.www.my_module.main.base.BaseApplication;


/**
 * 
 * <提示公共类> <功能详细描述>
 * 
 * @author wangtao
 * @version [版本号, 2014-4-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class ToastUtil
{
    /**
     * 
     * <显示用户所传信息> <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    public static void makeText(Context context, String msg)
    {
        if (BaseApplication.currentActivity.equals(context.getClass().getName()))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    
    /**
     * 
     * <显示失败信息 > <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    public static void showError(Context context)
    {
        makeText(context,"网络连接错误 请稍后再试");
    }

}
