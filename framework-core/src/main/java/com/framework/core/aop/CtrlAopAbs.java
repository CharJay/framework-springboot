package com.framework.core.aop;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.core.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.core.db.bean.RetMsg;


/** 
 * 拦截器： 
 */  
public class CtrlAopAbs {  
    private static final Logger LOGGER = LoggerFactory.getLogger(CtrlAopAbs.class);
      
    /** 
     * 拦截器具体实现 
     * @param pjp 
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。） 
     */  
    public Object interceptorInvoke(ProceedingJoinPoint pjp){  
        long beginTime = System.currentTimeMillis();  
        MethodSignature signature = (MethodSignature) pjp.getSignature();  
        Method method = signature.getMethod(); //获取被拦截的方法  
        String methodName = method.getName(); //获取被拦截的方法名  
          
          
        LOGGER.debug("{}请求开始", methodName);
          
        Object result = null;  
  
        Set<Object> allParams = new LinkedHashSet<>(); //保存所有请求参数，用于输出到日志中  
        Object[] args = pjp.getArgs();  
        for(Object arg : args){  
            //LOGGER.debug("arg: {}", arg);
            if (arg instanceof Map<?, ?>) {  
                //提取方法中的MAP参数，用于记录进日志中  
                @SuppressWarnings("unchecked")  
                Map<String, Object> map = (Map<String, Object>) arg;  
  
                allParams.add(map);  
            }else if(arg instanceof HttpServletRequest){  
                HttpServletRequest request = (HttpServletRequest) arg;  
                  
                //获取query string 或 posted form data参数  
                Map<String, String[]> paramMap = request.getParameterMap();  
                if(paramMap!=null && paramMap.size()>0){  
                    allParams.add(paramMap);  
                }  
            }else if(arg instanceof HttpServletResponse){  
                //do nothing...  
            }else{  
                //allParams.add(arg);  
            }  
        }  
        LOGGER.debug( "请求参数:{}", allParams );
          
        RetMsg ret = new RetMsg<>();
        try {  
            if(result == null){  
                // 一切正常的情况下，继续执行被拦截的方法  
                result = pjp.proceed();  
                
                ret.setData( result );
                
            }  
        } catch (BusinessException be) {
            LOGGER.debug("exception: ", be);
            ret.setSuccess( false );
            ret.setCode( be.getErrCode() );
            ret.setMsg( be.getMessage() );
        } catch (Throwable e) {  
            LOGGER.debug("exception: ", e);
            ret.setSuccess( false );
            ret.setCode( 1001 );
            ret.setMsg( e.getMessage() );
        } finally {
            
          long costMs = System.currentTimeMillis() - beginTime;  
          LOGGER.debug("{}请求结束，耗时：{}ms", methodName, costMs);
        }

        return ret;  
    }  
      
} 