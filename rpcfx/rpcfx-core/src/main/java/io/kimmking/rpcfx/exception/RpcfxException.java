package io.kimmking.rpcfx.exception;

/**
 * @author Zhu WeiJie
 * @date 2021/8/18
 **/
public class RpcfxException extends RuntimeException{

    public RpcfxException(String message) {
        super(message);
    }

    public RpcfxException(String message, Throwable cause) {
        super(message, cause);
    }
}
