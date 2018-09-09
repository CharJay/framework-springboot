package com.framework.core.utils.http;

import java.net.HttpURLConnection;
import java.util.List;

import org.apache.http.cookie.Cookie;

public class ResponseWrap extends HttpMessageWrap {
    
    private int              statusCode;
    
    private RequestWrap      request;
    
    private String           errorResponseAsString;
    private List<Cookie> cookies;
    
    public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	public ResponseWrap( RequestWrap request ) {
        this.request = request;
    }
    
    public boolean isSuccessful() {
        return statusCode / 100 == HttpURLConnection.HTTP_OK / 100;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode( int statusCode ) {
        this.statusCode = statusCode;
    }
    
    public RequestWrap getRequest() {
        return request;
    }
    
    public void setRequest( RequestWrap request ) {
        this.request = request;
    }
    
    public String getErrorResponseAsString() {
        return errorResponseAsString;
    }
    
    public void setErrorResponseAsString( String errorResponseAsString ) {
        this.errorResponseAsString = errorResponseAsString;
    }
    
}
