package cn.nj.www.my_module.constant;

/**
 * Created by Administrator on 2015/11/5.
 */
public class IntentCode
{
    /**
     * 引导页跳转标识
     */
    public static final String GUIDE_JUMP = "guide_jump";

    /**
     * 注册手机号
     */
    public static String REGISTER_PHONE = "register_phone";
    /**
     * 是否是忘记密码
     */
    public static String EDIT_PASSWORD_IS_FORGET = "EDIT_PASSWORD_IS_FORGET";
    /**
     * 市
     */
    public static String CITY = "CITY";
    /**
     * 省
     */
    public static String PROVINCE = "PROVINCE";
    public static String CHOOSE_LOCATION = "CHOOSE_LOCATION";
    /**
     * 社区ID
     */
    public static String COMMUNITY_ID = "COMMUNITY_ID";
    /**
     * 评论的订单ID
     */
    public static String C_ORDER_ID = "ORDERID";
    /**
     * 社区发帖子
     */
    public static String COMMUNITY_PUBLIC = "COMMUNITY_PUBLIC";
    /**
     * 点击帖子的图片数
     */
    public static String COMMUNITY_IMAGE_DATA = "COMMUNITY_IMAGE_DATA";
    /**
     *  打开收货地址后的操作  0 新加  1 修改
     */
    public static String EDIT_ADDRESS = "EDIT_ADDRESS";
    /**
     *  修改收货地址 的列表位置
     */
    public static String EDIT_ADDRESS_POSITION = "EDIT_ADDRESS_POSITION";
    /**
     *  修改收货地址 的详细信息
     */
    public static String EDIT_ADDRESS_BEAN_DETAIL = "EDIT_ADDRESS_BEAN_DETAIL";
    /**
     *  订单 进入地址列表
     */
    public static String CHOOSE_ADDRESS_WITH_PHONE = "CHOOSE_ADDRESS_WITH_PHONE";
    /**
     * contentID
     */
    public static String contentID = "contentID";
    public static String shopId = "shopId";
    /**
     * 搜索关键字
     */
    public static String SEARCH_KEYORD = "SEARCH_KEYORD";
    public static String SEARCH_TITLE = "SEARCH_TITLE";
    /**
     * 支付宝结果
     */
    public static String ZFB_RESULT = "ZFB_RESULT";
    /**
     * 选择到的地址
     */
    public static String CHOOSE_ADDRESS = "CHOOSE_ADDRESS";
    public static String ORDER_SIGN = "ORDER_SIGN";




    /**
     * 通用WebView标题
     */
    public static String COMMON_WEB_VIEW_TITLE = "common_web_view_title";

    /**
     * 通用WebView链接
     */
    public static String COMMON_WEB_VIEW_URL = "common_web_view_url";

    public static String COMMON_WEB_VIEW_URL_INSTRUCTION = "common_web_view_instruction";

    public static String COMMON_WEB_VIEW_URL_TAG = "common_web_view_url_tag";

    //相册中图片对象集合
    public static final String EXTRA_IMAGE_LIST = "image_list";
    //相册名称
    public static final String EXTRA_BUCKET_NAME = "buck_name";
    //可添加的图片数量
    public static final String EXTRA_CAN_ADD_IMAGE_SIZE = "can_add_image_size";
    //当前选择的照片位置
    public static final String EXTRA_CURRENT_IMG_POSITION = "current_img_position";
    public static final String EXTRA_SERVICETYPE="serviceType";
    //传递订单goodslist
    public static final String ORDER_GOODS_LIST = "goods_list";
    //是新生成订单0or代付款订单1
    public static final String ORDER_STATE = "ORDER_STATE";
    //传的分类的type
    public static final String C_TYPE = "C_TYPE";
}
