package com.nannong.mall.tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;
import com.nannong.mall.activity.mine.LoginActy;

import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.ToastUtil;

/**
 * Created by huqing on 2016/8/24.
 */
public class AppUtil
{
    /**
     * 判断是否登录，如果登录就跳转到toActy，否则跳转到登录页
     */
    public static void toActyOtherwiseLogin(Context context, Class toActy)
    {
        if (isLogin())
        {
            context.startActivity(new Intent(context, toActy));
        }
        else
        {
            context.startActivity(new Intent(context, LoginActy.class));
        }
    }


    public static void jumpToActivity(Context context, Class c)
    {
        context.startActivity(new Intent(context, c));
    }

    /**
     * 根据userName是否存在判断用户登录状况
     *
     * @return
     */
    public static boolean isLogin()
    {
        if (GeneralUtils.isNotNullOrZeroLenght(Global.getUserName()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static void initEMConnectListener(final Activity context){
        EMClient.getInstance().addConnectionListener(new EMConnectionListener() {
            @Override
            public void onConnected() {

            }

            @Override
            public void onDisconnected(final int error) {
             context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if(error == EMError.USER_REMOVED){
                            // 显示帐号已经被移除
                        }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                            // 显示帐号在其他设备登录
                            ToastUtil.makeText(context,"您的账号已在别地方登录了，请重新登陆~");
                            Global.loginOut(context);
                            context.startActivity(new Intent(context,LoginActy.class));
                        } else {
                            if (NetUtils.hasNetwork(context)) {
                                //连接不到聊天服务器
                                ToastUtil.showError(context);
                            }
                            else {
                                //当前网络不可用，请检查网络设置
                                ToastUtil.showError(context);
                            }
                        }
                    }
                });
            }
        });

    }

}
