package com.framework.core.exception;

public class BusinessException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = -5881435016051170825L;

    private int               errCode          = -1;
                                               
    public BusinessException() {
        super();
    }
    
    public BusinessException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
    
    public BusinessException( String message, Throwable cause ) {
        super( message, cause );
    }
    
    public BusinessException( String message ) {
        super( message );
    }
    
    public BusinessException( Throwable cause ) {
        super( cause );
    }
    
    public BusinessException( int errCode, String message ) {
        super( message );
        this.errCode = errCode;
    }
    
    public int getErrCode() {
        return errCode;
    }
    
    public void setErrCode( int errCode ) {
        this.errCode = errCode;
    }
    
}
