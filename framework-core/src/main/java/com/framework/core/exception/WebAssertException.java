package com.framework.core.exception;

public class WebAssertException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = -5881435016051170825L;

    private int               errCode          = -1;
                                               
    public WebAssertException() {
        super();
    }
    
    public WebAssertException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
    
    public WebAssertException( String message, Throwable cause ) {
        super( message, cause );
    }
    
    public WebAssertException( String message ) {
        super( message );
    }
    
    public WebAssertException( Throwable cause ) {
        super( cause );
    }
    
    public WebAssertException( int errCode, String message ) {
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
