package net.vipmro.search.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * map工具
 *
 * @author fengxiangyang
 * @date 2018/11/30
 */
public class MapUtils {

    /**
     * 转换为map
     * @param objects
     * @return
     */
    public static <K, V> Map<K, V> toMap(Object... objects) {
        if (objects.length % 2 != 0) {
            throw new IllegalArgumentException("传入参数个数错误");
        }
        Map<Object, Object> map = new HashMap<>(16);
        Object key = null;
        for (int i = 0; i < objects.length; i++) {
            if (i % 2 == 0) {
                key = objects[i];
            } else {
                map.put(key, objects[i]);
            }
        }
        return (Map<K, V>) map;
    }

}
