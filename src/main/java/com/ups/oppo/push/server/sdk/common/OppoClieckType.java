package com.ups.oppo.push.server.sdk.common;

import java.util.HashMap;
import java.util.Map;

/**
 * User: jasperxgwang
 * Date: 2018-5-30 9:53
 */
public enum OppoClieckType {

    APP(0, "打开应用"),
    ACTION(1, "打开应用内页（activity的intent action）"),
    URI(2, "打开URI页面"),
    ACTIVITY(4, "打开应用内页（activity）");


    private Integer desc;
    private String value;

    OppoClieckType(Integer desc, String value) {
        this.desc = desc;
        this.value = value;
    }


    public Integer getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }


    private static final Map<Integer, OppoClieckType> ENUMMAP = new HashMap<Integer, OppoClieckType>();

    static {
        for (OppoClieckType clickType : OppoClieckType.values()) {
            ENUMMAP.put(clickType.getDesc(), clickType);
        }
    }

    public static OppoClieckType fromValue(Integer desc) {
        return ENUMMAP.get(desc);
    }
}
