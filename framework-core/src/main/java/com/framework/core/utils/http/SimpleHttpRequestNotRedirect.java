package com.framework.core.utils.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.framework.core.utils.file.CustomFileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleHttpRequestNotRedirect {
    private static Logger LOGGER=LoggerFactory.getLogger(SimpleHttpRequestNotRedirect.class);
    private SimpleHttpRequestNotRedirect() {
    }
    
    private static SimpleHttpRequestNotRedirect single;
    
    public static SimpleHttpRequestNotRedirect getInstance() {
        return single == null ? (single = new SimpleHttpRequestNotRedirect()) : single;
    }
    
    private RequestConfig config;
    
    //TODO http重试策略
    private CloseableHttpClient defClient;
    
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
            
            // RequestConfig设置
            config = RequestConfig.custom()
                    .setRedirectsEnabled( false )
                    .setSocketTimeout(request.getSocketTimeOut()).setConnectTimeout(request.getConnectTimeOut()).setConnectionRequestTimeout(request.getConnectRequestTimeOut()).build();

            //TODO http重试策略
            defClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setRedirectStrategy( new LaxRedirectStrategy() ).build();

            LOGGER.debug(  "SimpleHttpRequestNotRedirect.send:{}", httpRequest );
            
            CloseableHttpResponse apacheHttpResponse = defClient.execute( httpRequest );
            
            response = buildResponse( request, apacheHttpResponse );
            
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
    
    private ResponseWrap buildResponse( RequestWrap request, HttpResponse apacheHttpResponse ) throws IOException {
        
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
        
        return response;
    }
    
    private void readAndSetErrorResponse( InputStream originalContent, ResponseWrap response ) throws IOException {
        byte[] contentBytes = IOUtils.toByteArray( originalContent );
        response.setErrorResponseAsString( new String( contentBytes ) );
        response.setContent( new ByteArrayInputStream( contentBytes ) );
    }
    
    public static void main( String[] args ) {
        String url = "http://read.qwlove.cn/read/common/home.htm?openId=ouqxbw2SFsouSNrLRAtmmAL6qma0&agent=masses";
        RequestWrap req = new RequestWrap();
//Host: read.qwlove.cn
//Connection: keep-alive
//Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
//Upgrade-Insecure-Requests: 1
//User-Agent: Mozilla/5.0 (Linux; Android 6.0; LA-S31 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36 MicroMessenger/6.3.32.960 NetType/WIFI Language/zh_CN
//Accept-Encoding: gzip, deflate
//Accept-Language: zh-CN,en-US;q=0.8
//Cookie: JSESSIONID=D7BA144FC20B97D480F9C89C96911111
//X-Requested-With: com.tencent.mm
        
//        Host: read.qwlove.cn
//        Connection: keep-alive
//        Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
//        User-Agent: Mozilla/5.0 (Linux; Android 6.0; LA-S31 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36 MicroMessenger/6.3.32.960 NetType/WIFI Language/zh_CN
//        Accept-Encoding: gzip, deflate
//        Accept-Language: zh-CN,en-US;q=0.8
        
        //head
        req.addHeader( "Host", "read.qwlove.cn" );
        req.addHeader( "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" );
        req.addHeader( "Content-Type", "application/x-www-form-urlencoded" );
        req.addHeader( "User-Agent",
                "Mozilla/5.0 (Linux; Android 6.0; LA-S31 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36 MicroMessenger/6.3.32.960 NetType/WIFI Language/zh_CN" );
        req.addHeader( "Accept-Encoding", "gzip, deflate" );
        req.addHeader( "Accept-Language", "zh-CN,en-US;q=0.8" );

        req.setUri( url );
        
        req.setMethod( HttpMethod.GET );
        String retmsg = null;
        try {
            retmsg  = SimpleHttpRequestNotRedirect.getInstance().send( req, new ResponseHandler<String>() {
                
                @Override
                public boolean complete( ResponseWrap response ) throws IOException {
                    // TODO Auto-generated method stub
                    return true;
                }
                
                @Override
                public String success( ResponseWrap response ) throws IOException {
                    Map<String, String> hh = response.getHeaders();
                    
                    byte[] b = IOUtils.toByteArray( response.getContent() );
                    String ret = new String( b );
                    System.out.println( ret );
                    String vv = hh.get( "Location" );
                    System.out.println( vv );
                    
                    return ret;
                }
                
                @Override
                public String error( ResponseWrap response ) throws IOException {
                    Map<String, String> hh = response.getHeaders();
                    //Location: http://mp.weixin.qq.com/s?__biz=MjM5NjM4MTczMQ==&mid=2651241475&idx=1&sn=e4563a05c0909a909cb9822e467d87fb&chksm=bd18094f8a6f8059bd4d01828214fa5ccee34eeb4a836c7cc8934024e2b92f1405f8c15a946c#wechat_redirect
                    String vv = hh.get( "Location" );
                    System.out.println( vv );
                    //http://mp.weixin.qq.com/s?__biz=MjM5NjM4MTczMQ==&mid=2651241475&idx=1&sn=e4563a05c0909a909cb9822e467d87fb&chksm=bd18094f8a6f8059bd4d01828214fa5ccee34eeb4a836c7cc8934024e2b92f1405f8c15a946c#wechat_redirect
                    //http://read.qwlove.cn/read/finish.htm?retCode=3&retMsg=10:26:40
                    //http://read.qwlove.cn/read/busy.htm
                    System.out.println( vv.contains(  "read.qwlove.cn" ) );
                    return vv;
                }
            } );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
