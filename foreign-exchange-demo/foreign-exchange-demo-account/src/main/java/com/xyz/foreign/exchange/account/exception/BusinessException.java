package com.xyz.foreign.exchange.account.exception;

/**
 * @author Zhu WeiJie
 * @date 2021/8/18
 **/
public class BusinessException extends RuntimeException{

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
