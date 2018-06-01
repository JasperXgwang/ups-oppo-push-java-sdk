package com.ups.oppo.push.server.sdk.util;

import com.ups.oppo.push.server.sdk.exception.InvalidAuthTokenException;

import java.io.IOException;

/**
 * User: jasperxgwang
 * Date: 2018-5-30 15:35
 */
public interface ExecHttpApplication<T> {

    /**
     * 执行请求
     *
     * @return
     * @throws IOException
     */
    ResultPack<T> request() throws IOException;

    /**
     * 刷新token
     *
     * @return true 正常  false接口响应失败
     * @throws InvalidAuthTokenException 接口响应token非法
     */
    boolean refreshToken() throws InvalidAuthTokenException;
}
