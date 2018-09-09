package com.framework.core.utils.http;

import java.io.IOException;

public interface ResponseHandler<T> {
    
    /**
     * /**
     * 请求结束后调用此方法
     *
     * @param response
     * @return 是否继续执行后续方法(success、error)
     * @throws IOException
     */
    boolean complete( ResponseWrap response ) throws IOException;
    
    /**
     * http status code为OK时调用（code/100 == 2）
     *
     * @param response
     * @return
     * @throws IOException
     */
    T success( ResponseWrap response ) throws IOException;
    
    /**
     * http status code非OK时调用（code/100 != 2）
     *
     * @param response
     * @return
     * @throws IOException
     */
    T error( ResponseWrap response ) throws IOException;
    
}
