package cn.nj.www.my_module.tools;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huqing on 2016/7/8.
 */
public class MapToJsonUtil {
    /**
     * 将map转化为json字符串
     *
     * @param map
     * @param keyName
     * @param valueName
     * @return
     */
    public static String MapToJson(Map<? extends Object, ? extends Object> map,
                                   String keyName, String valueName) {
        String string = "";
        if (map != null && keyName != null && valueName != null) {
            List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
            for (Map.Entry<? extends Object, ? extends Object> m : map.entrySet()) {
                Map<Object, Object> newMap = new HashMap<Object, Object>();
                newMap.put(keyName, m.getKey());
                newMap.put(valueName, m.getValue());
                list.add(newMap);
            }
            Gson gson = new Gson();
            string = gson.toJson(list);
        }
        return string;
    }
}
