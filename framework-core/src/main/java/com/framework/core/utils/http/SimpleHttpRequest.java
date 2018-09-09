package com.framework.core.utils.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.framework.core.utils.file.CustomFileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 便捷的HTTP请求实例。
 */
public class SimpleHttpRequest {

    private static Logger LOGGER=LoggerFactory.getLogger(SimpleHttpRequest.class);

    private SimpleHttpRequest() {
    }
    
    private static SimpleHttpRequest single;
    
    public static SimpleHttpRequest getInstance() {
        return single == null ? (single = new SimpleHttpRequest()) : single;
    }
    
    public <T> T send( RequestWrap request, ResponseHandler<T> responseHandler ) throws IOException {
        boolean isPost = HttpMethod.POST == request.getMethod();
        String paramStr = CustomHttpUtils.paramToQueryString( request.getParameters(), request.getRemoteCharset() );
        
        ResponseWrap response = null;
        
        try {
            if (paramStr != null) {
                if (isPost) {
                    request.setStringContent( paramStr, request.getLocalCharset() );
                } else {
                    String uri = request.getUri();
                    if (uri.lastIndexOf( "?" ) == -1) {
                        uri += "?" + paramStr;
                    } else {
                        uri += "&" + paramStr;
                    }
                    request.setUri( uri );
                }
            }
            
            HttpRequestBase httpRequest = HttpRequestFactory.create( request );
            LOGGER.debug( "SimpleHttpRequest.send:{}", httpRequest );
			RequestConfig config = RequestConfig.custom().setSocketTimeout(request.getSocketTimeOut())
					.setCookieSpec(CookieSpecs.DEFAULT).setRedirectsEnabled(request.isFllowerRedirect())
					.setConnectTimeout(request.getConnectTimeOut())
					.setConnectionRequestTimeout(request.getConnectRequestTimeOut()).build();
			// HttpClientBuilder
			HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(config);
			if (request.isFllowerRedirect())
				builder.setRedirectStrategy(new LaxRedirectStrategy());
			if (!request.isEnableRetry())// 如果不允许重试，关闭重试策略
				builder.disableAutomaticRetries();
			// build client
			CloseableHttpClient defClient = builder.build();
			// create http context
			HttpClientContext httpContext = HttpClientContext.create();
			CookieStore cookieStore = new BasicCookieStore();
			if (request.getCookies() != null && !request.getCookies().isEmpty())
				for (Cookie c : request.getCookies()) {
					cookieStore.addCookie(c);
				}
			httpContext.setCookieStore(cookieStore);
			
            CloseableHttpResponse apacheHttpResponse = defClient.execute( httpRequest, httpContext);
            
            response = buildResponse( request, apacheHttpResponse, httpContext );
            T result = null;
            // do complete method first
            if (responseHandler.complete( response )) {
                if (response.isSuccessful()) {
                    result = responseHandler.success( response );
                } else {
                    result = responseHandler.error( response );
                }
            }
            
            return result;
        } finally {
            if (request != null && request.isAutoClose()) {
                CustomFileUtils.closeStreams( request );
            }
            if (response != null && response.isAutoClose()) {
                CustomFileUtils.closeStreams( response );
            }
        }
        
    }
    
    private ResponseWrap buildResponse( RequestWrap request, HttpResponse apacheHttpResponse, HttpClientContext httpContext ) throws IOException {
        
        ResponseWrap response = new ResponseWrap( request );
        response.setUri( request.getUri() );
        
        if (apacheHttpResponse.getStatusLine() != null) {
            response.setStatusCode( apacheHttpResponse.getStatusLine().getStatusCode() );
        }
        
        if (apacheHttpResponse.getEntity() != null) {
            if (response.isSuccessful()) {
                response.setContent( apacheHttpResponse.getEntity().getContent() );
            } else {
                readAndSetErrorResponse( apacheHttpResponse.getEntity().getContent(), response );
            }
        }
        
        for (Header header : apacheHttpResponse.getAllHeaders()) {
            if (HttpHeaders.CONTENT_LENGTH.equals( header.getName() )) {
                response.setContentLength( Long.parseLong( header.getValue() ) );
            }
            if("Set-Cookie".equalsIgnoreCase( header.getName() )){
                response.addSetCookies( header.getValue() );
                String cc = response.getHeaders().get( header.getName() );
                if(cc==null){
                    response.addHeader( header.getName(), header.getValue() );
                }else{
                    response.addHeader( header.getName(), cc+";"+header.getValue() );
                }
            }else{
                response.addHeader( header.getName(), header.getValue() );
            }
        }
        List<Cookie> cookies = httpContext.getCookieStore().getCookies();
		response.setCookies(cookies);
        
        return response;
    }
    
    private void readAndSetErrorResponse( InputStream originalContent, ResponseWrap response ) throws IOException {
        byte[] contentBytes = IOUtils.toByteArray( originalContent );
        response.setErrorResponseAsString( new String( contentBytes ) );
        response.setContent( new ByteArrayInputStream( contentBytes ) );
    }
    
}
