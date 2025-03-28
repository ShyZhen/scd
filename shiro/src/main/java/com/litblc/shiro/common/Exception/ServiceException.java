package com.litblc.shiro.common.Exception;

import java.io.Serial;

/**
 * 业务异常
 */
public class ServiceException extends RuntimeException {

    @Serial
    private final static long serialVersionUID = -3863688430656815323L;

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
