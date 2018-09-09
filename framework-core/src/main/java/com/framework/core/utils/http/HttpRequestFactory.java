package com.framework.core.utils.http;

import java.util.Map.Entry;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.BasicHttpEntity;

public class HttpRequestFactory {
    
    public static HttpRequestBase create( RequestWrap request ) {
        String uri = request.getUri();
        HttpRequestBase httpRequest;
        HttpMethod method = request.getMethod();
        
        switch (method) {
        case POST:
            HttpPost postMethod = new HttpPost( uri );
            
            if (request.getContent() != null) {
                String contentType = request.getHeaders().get( HttpHeadersKey.CONTENT_TYPE );
                BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
                basicHttpEntity.setContent( request.getContent() );
                basicHttpEntity.setContentLength( request.getContentLength() );
                basicHttpEntity.setContentType( contentType );
                postMethod.setEntity( basicHttpEntity );
            }
            
            httpRequest = postMethod;
            break;
        case PUT:
            HttpPut putMethod = new HttpPut( uri );
            
            if (request.getContent() != null) {
                String contentType = request.getHeaders().get( HttpHeadersKey.CONTENT_TYPE );
                BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
                basicHttpEntity.setContent( request.getContent() );
                basicHttpEntity.setContentLength( request.getContentLength() );
                basicHttpEntity.setContentType( contentType );
                putMethod.setEntity( basicHttpEntity );
            }
            
            httpRequest = putMethod;
            break;
        case GET:
            httpRequest = new HttpGet( uri );
            break;
        case DELETE:
            httpRequest = new HttpDelete( uri );
            break;
        case HEAD:
            httpRequest = new HttpHead( uri );
            break;
        case OPTIONS:
            httpRequest = new HttpOptions( uri );
            break;
        default:
            throw new IllegalArgumentException( "Unknown HTTP method name: " + method );
        }
        
        configureRequestHeaders( request, httpRequest );
        
        return httpRequest;
    }
    
    private static void configureRequestHeaders( RequestWrap request, HttpRequestBase httpRequest ) {
        for (Entry<String, String> entry : request.getHeaders().entrySet()) {
            if (entry.getKey().equalsIgnoreCase( HttpHeadersKey.CONTENT_LENGTH )
                    || entry.getKey().equalsIgnoreCase( HttpHeadersKey.HOST )) {
                continue;
            }
            
            httpRequest.addHeader( entry.getKey(), entry.getValue() );
        }
        
        if (httpRequest.getHeaders( HttpHeadersKey.CONTENT_TYPE ) == null
                || httpRequest.getHeaders( HttpHeadersKey.CONTENT_TYPE ).length == 0) {
            httpRequest.addHeader( HttpHeadersKey.CONTENT_TYPE, "application/x-www-form-urlencoded; " + "charset="
                    + request.getLocalCharset().toLowerCase() );
        }
    }
}
