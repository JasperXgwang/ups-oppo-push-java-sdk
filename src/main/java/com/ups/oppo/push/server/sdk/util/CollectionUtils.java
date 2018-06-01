package com.ups.oppo.push.server.sdk.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxinguo on 2016-8-23.
 */
public class CollectionUtils {


    /**
     * Return {@code true} if the supplied Collection is {@code null}
     * or empty. Otherwise, return {@code false}.
     *
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Return {@code true} if the supplied Map is {@code null}
     * or empty. Otherwise, return {@code false}.
     *
     * @param map the Map to check
     * @return whether the given Map is empty
     */
    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }


    /**
     * 将List中的数据组成用冒号分隔的字符串，如a;b;c
     *
     * @param strList
     * @return
     */
    public static String list2Str(Collection<String> strList) {
        String resultStr = "";
        if (!CollectionUtils.isEmpty(strList)) {
            List<String> list = new ArrayList<String>(strList);
            for (int i = 0; i < list.size(); i++) {
                resultStr = resultStr + list.get(i) + ';';
            }
            resultStr = resultStr.substring(0, resultStr.length() - 1);
        }
        return resultStr;
    }
}
