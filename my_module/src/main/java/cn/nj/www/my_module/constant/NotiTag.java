package cn.nj.www.my_module.constant;

import android.content.Context;

import cn.nj.www.my_module.main.base.BaseApplication;


/**
 * Created by Administrator on 2015/11/5.
 */
public class NotiTag
{
    /**
     * 关闭堆栈界面
     */
    public static final String TAG_CLOSE = "close";

    /**
     * 关闭当前界面
     */
    public static final String TAG_CLOSE_ACTIVITY = "close_activity";
    public static final String TAG_BACK_ACTIVITY = "TAG_BACK_ACTIVITY";

    /**
     * 错误页面
     */
    public static final String TAG_ERROR_VIEW = "TAG_ERROR_VIEW";


    /**
     * 右边按钮操作
     */
    public static final String TAG_DO_RIGHT = "do_right";

    /**
     * 登录成功
     */
    public static final String TAG_LOGIN_SUCCESS = "TAG_LOGIN_SUCCESS";

    /**
     * 用户退出
     */
    public static final String TAG_USER_EXIT = "TAG_USER_EXIT";
    /**
     * 下载图片
     */
    public static final String TAG_DOWN_IMG = "TAG_DOWN_IMG";

    public static final String TAG_USER_EXIT_APP = "TAG_USER_EXIT_APP";
    /**
     * 付款成功
     */
    public static final String TAG_PAY_SUCCESS = "TAG_PAY_SUCCESS";
    /**
     * 头像选择成功
     */
    public static final String TAG_USER_HEAD_CHOOSE = "TAG_USER_HEAD_CHOOSE";
    /**
     * 图片上传成功
     */
    public static final String TAG_UPLOAD_PICS_SUCCESS = "TAG_UPLOAD_PICS_SUCCESS";
    /**
     * 选择地址成功
     */
    public static final String TAG_CHOOSE_ADDRESS_OK = "TAG_CHOOSE_ADDRESS_OK";
    /**
     * 获取到商品介绍的url
     */
    public static final String TAG_PRODUCT_URL = "TAG_PRODUCT_URL";
    /**
     * 帖子编辑
     */
    public static final String TAG_EDIT_TZ = "TAG_EDIT_TZ";

    /**
     * 用户定位成功
     */
    public static final String TAG_LOCATION_SUCCESS = "TAG_LOCATION_SUCCESS";
    public static final String TAG_LOCATION_CITY_SUCCESS = "TAG_LOCATION_CITY_SUCCESS";
    /**
     * 时间选择成功
     */
    public static final String TAG_CHOOSE_TIME_OK = "TAG_CHOOSE_TIME_OK";

    /**
     * webView tag,开始 ,错误 , 结束,刷新
     */
    public static final String TAG_WEB_VIEW_START = "web_view_start";

    public static final String TAG_WEB_VIEW_ERROR = "web_view_error";

    public static final String TAG_WEB_VIEW_FINISH = "web_view_finish";

    public static final String TAG_WEB_VIEW_REFRESH = "web_view_refresh";

    /**
     * 省市选择 Dialog,
     */
    public static final String TAG_CHOOSE_CITY_DIALOG = "CHOOSECITY_DIALOG";

    /**
     * 在社区中进入动态详情，切换MainActivity当前Fragment
     */
    public static final String TAG_INDEX_CHANGE_FRAGMENT = "TAG_INDEX_CHANGE_FRAGMENT";

    /**
     * 更改收货地址
     */
    public static final String TAG_CHANGE_RECEIVE_ADDRESS = "TAG_CHANGE_RECEIVE_ADDRESS";

    /**
     * 添加到购物车
     */
    public static final String TAG_ADD_CAR = "TAG_ADD_CAR";
    /**
     * 更改购买数量
     */
    public static final String TAG_CHANGE_BUY_NUM = "TAG_CHANGE_BUY_NUM";
    /**
     * 更改选中样式
     */
    public static final String TAG_CHANGE_STYLE = "TAG_CHANGE_STYLE";
    /**
     * 立即购买
     */
    public static final String TAG_BUY_NOW = "TAG_BUY_NOW";
    /**
     * 弹框取消
     */
    public static final String TAG_DIALOG_LEFT1 = "TAG_DIALOG_LEFT1";
    /**
     * 弹框确定
     */
    public static final String TAG_DIALOG_RIGHT1 = "TAG_DIALOG_RIGHT1";
    /**
     * 新增地址信息
     */
    public static final String NEW_SAVE_ADDRESS_RESULT = "NEW_SAVE_ADDRESS_RESULT";
    /**
     * 选择定位的城市,城市列表中选择
     */
    public static final String TAG_CHOOSE_CITY = "TAG_CHOOSE_CITY";
    /**
     *  用户设置是否要发票成功
     */
    public static final String TAG_SET_BILL= "TAG_SET_BILL";
    /**
     *  初始化成功
     */
    public static final String TAG_APP_INIT_SUCCESS= "TAG_APP_INIT_SUCCESS";

    /**
     * 在当前界面操作tag
     */
    public static boolean equalsTags(Context context, String tagOne, String tagTwo)
    {
        return tagOne.equals(tagTwo) && BaseApplication.currentActivity.equals(context.getClass().getName());
    }
    /**
     * 取消删除浏览历史
     */
    public static final String TAG_DEL_GOODS_CANCEL = "TAG_DEL_GOODS_CANCEL";
    /**
     * 确定删除浏览历史
     */
    public static final String TAG_DEL_GOODS_OK = "TAG_DEL_GOODS_OK";
    /**
     * 跳转到社区
     */
    public static final String TAG_TO_COMMUNITY = "TAG_TO_COMMUNITY";
}
