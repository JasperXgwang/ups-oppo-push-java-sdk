package com.ups.oppo.push.server.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * User: jasperxgwang
 * Date: 2018-5-31 10:07
 */
public class ReflectUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

    public static Map<String, String> getObjAttr(Object obj) {
        // 获取对象obj的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        Map<String, String> result = new HashMap<String, String>(fields.length);
        for (Field field : fields) {
            // 对于每个属性，获取属性名
            String varName = field.getName();
            try {
                boolean access = field.isAccessible();
                if (!access) {
                    field.setAccessible(true);
                }

                //从obj中获取field变量
                Object o = field.get(obj);
                if (o != null && StringUtils.isNotBlank(o.toString())) {
                    result.put(varName, o.toString());
                }
                if (!access) {
                    field.setAccessible(false);
                }
            } catch (Exception ex) {
                logger.error("getObjAttr error", ex);
            }
        }
        return result;
    }


}
