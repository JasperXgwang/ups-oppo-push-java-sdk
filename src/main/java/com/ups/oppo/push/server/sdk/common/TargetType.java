package com.ups.oppo.push.server.sdk.common;

/**
 * User: jasperxgwang
 * Date: 2018-5-30 9:53
 */
public enum TargetType {

    REG_ID(2),
    ALIAS(3),
    TAG(4);

    private int type;

    public int getType() {
        return type;
    }

    TargetType(int type) {
        this.type = type;
    }
}
