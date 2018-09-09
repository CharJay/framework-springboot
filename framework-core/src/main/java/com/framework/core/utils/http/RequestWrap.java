package com.framework.core.utils.http;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;

public class RequestWrap extends HttpMessageWrap {

    private HttpMethod          method;
    private Map<String, String> parameters;
    private String              remoteCharset         = "UTF-8";
    private String              localCharset          = "UTF-8";
    private List<Cookie> cookies;
    /**
     * socketTimeout 数据传输处理时间
     */
    private int                 socketTimeOut        = -1;
    /**
     * connectionRequestTimeout 从连接池中后去连接的timeout时间
     */
    private int                 connectRequestTimeOut = -1;
    /**
     * connectTimeout 建立连接的timeout时间
     */
    private int                 connectTimeOut        = -1;
    /**
     * 是否跟随重定向
     */
    private boolean fllowerRedirect = true;
    /**
     * 是否允许重试
     */
    private boolean enableRetry = true;

    public RequestWrap ajax() {
        addHeader("X-Requested-With", "XMLHttpRequest");
        return this;
    }

    public static RequestWrap newGet(String uri) {
        return newGet(uri, null);
    }

    public static RequestWrap newGet(String uri, Map<String, String> parameters) {
        RequestWrap request = new RequestWrap();
        request.setUri(uri);
        request.setMethod(HttpMethod.GET);
        request.setParameters(parameters);
        return request;
    }

    public static RequestWrap newPost(String uri) {
        return newPost(uri, null);
    }

    public static RequestWrap newPost(String uri, Map<String, String> parameters) {
        RequestWrap request = new RequestWrap();
        request.setUri(uri);
        request.setMethod(HttpMethod.POST);
        request.setParameters(parameters);
        return request;
    }

    public RequestWrap addParameter(String key, String value) {
        if (this.parameters == null) {
            this.parameters = new LinkedHashMap<String, String>();
        }
        this.parameters.put(key, value);
        return this;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getRemoteCharset() {
        return remoteCharset;
    }

    public void setRemoteCharset(String remoteCharset) {
        this.remoteCharset = remoteCharset;
    }

    public String getLocalCharset() {
        return localCharset;
    }

    public void setLocalCharset(String localCharset) {
        this.localCharset = localCharset;
    }

    public int getConnectRequestTimeOut() {
        return connectRequestTimeOut;
    }

    public void setConnectRequestTimeOut(int connectRequestTimeOut) {
        this.connectRequestTimeOut = connectRequestTimeOut;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

	public boolean isFllowerRedirect() {
		return fllowerRedirect;
	}

	public void setFllowerRedirect(boolean fllowerRedirect) {
		this.fllowerRedirect = fllowerRedirect;
	}

	public boolean isEnableRetry() {
		return enableRetry;
	}

	public void setEnableRetry(boolean enableRetry) {
		this.enableRetry = enableRetry;
	}

	public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

}
