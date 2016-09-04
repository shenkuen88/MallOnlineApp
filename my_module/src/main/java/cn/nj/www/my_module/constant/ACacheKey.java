package cn.nj.www.my_module.constant;

/**
 * Created by huqing on 2016/7/11.
 * 需要缓存上次接口请求到的String数据的页面,在此处写上对应key值，
 *
 * 保存：mCache.put("key", valueString);
 * 获取：String valueString = mCache.getAsString("Key"); //记得加上valueString是否为空的判断
 */
public class ACacheKey {
    /**
     * 接口 XXXX 数据缓存
     */
    public static final String KEY = "KEY";
}
