package com.ups.oppo.push.server.sdk.exception;

import java.util.Collection;
import java.util.HashMap;

public class ErrorCode {

    private int value;

    private String description;

    private static HashMap<Integer, ErrorCode> intErrorCodeMap = new HashMap<Integer, ErrorCode>();

    /**********************************公共返回码定义********************************************/
    public static ErrorCode SERVICE_CURRENTLY_UNAVAILABLE = valueOf(Integer.valueOf(-1), "服务不可用， 此时请开发者稍候再试");
    public static ErrorCode SUCCESS = valueOf(Integer.valueOf(0), "成功， 只表明接口调用成功");
    public static ErrorCode INVALID_AUTHTOKEN = valueOf(Integer.valueOf(11), "不合法的 AuthToken");
    public static ErrorCode HTTP_ACTION_NOT_ALLOWED = valueOf(Integer.valueOf(12), "HTTP 方法不正确");
    public static ErrorCode APP_CALL_LIMITED = valueOf(Integer.valueOf(13), "应用调用次数超限，包含调用频率超限");
    public static ErrorCode INVALID_APP_KEY = valueOf(Integer.valueOf(14), "无效的 AppKey 参数");
    public static ErrorCode MISSING_APP_KEY = valueOf(Integer.valueOf(15), "缺少 AppKey 参数");
    public static ErrorCode INVALID_SIGNATURE = valueOf(Integer.valueOf(16), "sign 校验不通过，无效签名");
    public static ErrorCode MISSING_SIGNATURE = valueOf(Integer.valueOf(17), "缺少签名参数");
    public static ErrorCode MISSING_TIMESTAMP = valueOf(Integer.valueOf(18), "缺少时间戳参数");
    public static ErrorCode INVALID_TIMESTAMP = valueOf(Integer.valueOf(19), "非法的时间戳参数");
    public static ErrorCode INVALID_METHOD = valueOf(Integer.valueOf(20), "不存在的方法名");
    public static ErrorCode MISSING_METHOD = valueOf(Integer.valueOf(21), "缺少方法名参数");
    public static ErrorCode MISSING_VERSION = valueOf(Integer.valueOf(22), "缺少版本参数");
    public static ErrorCode INVALID_VERSION = valueOf(Integer.valueOf(23), "非法的版本参数， 用户传入的版本号格式错误， 必需为数字格式");
    public static ErrorCode UNSUPPORTED_VERSION = valueOf(Integer.valueOf(24), "不支持的版本号， 用户传入的版本号没有被提供");
    public static ErrorCode INVALID_ENCODING = valueOf(Integer.valueOf(25), "编码错误， 一般是用户做 http 请求的时候没有用 UTF-8 编码请求造成的");
    public static ErrorCode IP_BLACK_LIST = valueOf(Integer.valueOf(26), "IP 黑名单");
    public static ErrorCode ACCESS_DENIED = valueOf(Integer.valueOf(27), "没有此功能的权限，拒绝访问");
    public static ErrorCode APP_DISABLED = valueOf(Integer.valueOf(28), "应用不可用");
    public static ErrorCode MISSING_AUTH_TOKEN = valueOf(Integer.valueOf(29), "缺少 Auth Token 参数");
    public static ErrorCode API_PERMISSION_DENIED = valueOf(Integer.valueOf(30), "该应用没有 API 推送的权限");
    public static ErrorCode DATA_NOT_EXIST = valueOf(Integer.valueOf(31), "数据不存在");
    public static ErrorCode DATA_DUPLICATE = valueOf(Integer.valueOf(32), "数据重复");
    public static ErrorCode MISSING_REQUIRED_ARGUMENTS = valueOf(Integer.valueOf(40), "缺少必选参数，API 文档中设置为必选的参数是必传的， 请仔细核对文档");
    public static ErrorCode INVALID_ARGUMENTS = valueOf(Integer.valueOf(41), "参数错误，一般是用户传入参数非法引起的， 请仔细检查入参格式、范围是否一一对应");


    /**********************************应用级错误码********************************************/
    public static ErrorCode INVALID_REGISTRATION_ID = valueOf(Integer.valueOf(10000), "registration_id 非法或无效");
    public static ErrorCode REGISTRATION_ID_UNSUBSCRIBE = valueOf(Integer.valueOf(10001), "registration_id未订阅(包括推送开关关闭的设备)");
    public static ErrorCode INVALID_ALIAS_NAME = valueOf(Integer.valueOf(10002), "alias name 非法或无效");
    public static ErrorCode ALIAS_NAME_UNSUBSCRIBE = valueOf(Integer.valueOf(10003), "alias name 未订阅(包括推送开关关闭的设备)");

    public static Collection<ErrorCode> getAllErrorCodes() {
        return intErrorCodeMap.values();
    }

    private ErrorCode(int value) {
        this.value = value;
    }

    private ErrorCode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ErrorCode valueOf(int value) {
        return (ErrorCode) intErrorCodeMap.get(Integer.valueOf(value));
    }

    public static ErrorCode valueOf(int value, ErrorCode defaultIfMissing) {
        ErrorCode code = (ErrorCode) intErrorCodeMap.get(Integer.valueOf(value));
        if (code == null) {
            return defaultIfMissing;
        }
        return code;
    }

    public static ErrorCode valueOf(Integer code, String reason) {
        ErrorCode result = (ErrorCode) intErrorCodeMap.get(code);
        if (result == null) {
            result = new ErrorCode(code.intValue(), reason);
            intErrorCodeMap.put(code, result);
        }
        return result;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "value=" + value +
                ", description='" + description + '\'' +
                '}';
    }
}
