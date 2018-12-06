package net.vipmro.search.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fengxiangyang
 * @date 2018/12/6
 */
public class BeanUtils {

    /**
     * bean转化为map
     * @param bean
     * @return
     */
    public static Map<String, Object> toMap(Object bean) {
        Class type = bean.getClass();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    returnMap.put(propertyName, result);
                }
            }
        } catch (Exception e){

        }
        return returnMap;
    }
}
