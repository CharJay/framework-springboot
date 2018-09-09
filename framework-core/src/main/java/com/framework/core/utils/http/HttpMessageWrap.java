package com.framework.core.utils.http;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.framework.core.utils.file.CustomFileUtils;

public abstract class HttpMessageWrap implements Closeable {
    
    private String              uri;
    private Map<String, String> headers = new LinkedHashMap<String, String>();
    private List<String> setcookies = new ArrayList<>();
    private InputStream         content;
    private long                contentLength;
    private boolean             autoClose = true;
    
    public void setStringContent( String str, String charset ) throws IOException {
        byte[] bytes = charset != null ? str.getBytes( charset ) : str.getBytes();
        content = new ByteArrayInputStream( bytes );
        contentLength = bytes.length;
    }
    
    @Override
    public void close() {
        CustomFileUtils.closeStreams( content );
    }
    
    public HttpMessageWrap addHeader( String key, String value ) {
        this.headers.put( key, value );
        return this;
    }
    public HttpMessageWrap addSetCookies(String cstr){
        this.setcookies.add( cstr );
        return this;
    }
    
    public String getHeader(String name) {
        return headers.get(name);
    }
    
    public String getUri() {
        return this.uri;
    }
    
    public void setUri( String uri ) {
        this.uri = uri;
    }
    
    public Map<String, String> getHeaders() {
        return headers;
    }
    
    public void setHeaders( Map<String, String> headers ) {
        this.headers = headers;
    }
    
    public InputStream getContent() {
        return content;
    }
    
    public void setContent( InputStream content ) {
        this.content = content;
    }
    
    public long getContentLength() {
        return contentLength;
    }
    
    public void setContentLength( long contentLength ) {
        this.contentLength = contentLength;
    }
    
    public boolean isAutoClose() {
        return autoClose;
    }
    
    public void setAutoClose( boolean autoClose ) {
        this.autoClose = autoClose;
    }

    
    public List<String> getSetcookies() {
        return setcookies;
    }

    
    public void setSetcookies( List<String> setcookies ) {
        this.setcookies = setcookies;
    }
    
}
