package com.lengjiye.network.exception;

/**
 * error code
 */
public interface ErrorCodeConstant {
    /**
     * 请求成功
     */
    int REQUEST_SUCCESS = 0;
    /**
     * 未知错误
     */
    int UNKNOWN_ERROR = -10000;
    /**
     * 请求超时
     */
    int NETWORK_TIMEOUT = -10001;
    /**
     * 无网络链接
     */
    int NO_NETWORK = -10002;
    /**
     * json解析错误
     */
    int JSON_ERROR = -10003;
    /**
     * 登录失效
     */
    int LOGIN_INVALID = -1001;
}
