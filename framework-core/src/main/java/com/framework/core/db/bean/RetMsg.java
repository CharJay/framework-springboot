package com.framework.core.db.bean;

import com.framework.core.utils.helper.CodeEnum;

public class RetMsg<T> {
    
    private boolean success = true;
    private int code =0;
    private String msg = "";
    
    private T data;

    public RetMsg() {
    }
    public RetMsg( T data) {
        setData( data );
    }
    public RetMsg(int code,String msg) {
        this.code=code;
        this.msg=msg;
    }
	public static <T> RetMsg<T> success() {
		return new RetMsg<T>(0, "请求成功");
	}

	public static <T> RetMsg<T> success(T data) {
		RetMsg<T> RetMsg = new RetMsg<T>(0, "请求成功");
		RetMsg.setData(data);
		return RetMsg;
	}

	public static <T> RetMsg<T> error(Integer code, String msg) {
		return new RetMsg<T>(code, msg);
	}
	
	public static <T> RetMsg<T> error(CodeEnum codeEnum) {
		return new RetMsg<T>(codeEnum.getCode(), codeEnum.getDescr());
	}
    
    public boolean isSuccess() {
        return success;
    }

    
    public void setSuccess( boolean success ) {
        this.success = success;
    }

    
    public int getCode() {
        return code;
    }

    
    public void setCode( int code ) {
        this.code = code;
    }

    
    public String getMsg() {
        return msg;
    }

    
    public void setMsg( String msg ) {
        this.msg = msg;
    }

    
    public T getData() {
        return data;
    }

    
    public void setData( T data ) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "JsonOutBean [success=" + success + ", code=" + code + ", msg=" + msg + ", data=" + data + "]";
    }
    
    
}
