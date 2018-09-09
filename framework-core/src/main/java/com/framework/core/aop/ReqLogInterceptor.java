package com.framework.core.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 请求日志拦截器
 * 
 * @author
 */
public class ReqLogInterceptor extends HandlerInterceptorAdapter {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( ReqLogInterceptor.class );
    
    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
        // u-insert#other1#
        String possibleRealIp = request.getHeader( "X-Real-IP" );
        LOGGER.debug( "{} {}:{}", request.getRequestURI(), possibleRealIp==null? request.getRemoteAddr() : possibleRealIp, request.getRemotePort() );
        // u-insert#
        
        return true;
    }
    // u-insert#other2#
    // u-insert#
}
