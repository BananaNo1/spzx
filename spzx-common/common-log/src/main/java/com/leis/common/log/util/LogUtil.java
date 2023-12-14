package com.leis.common.log.util;

import com.alibaba.fastjson.JSON;
import com.leis.common.log.annotation.Log;
import com.leis.model.entity.system.SysOperLog;
import com.leis.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LogUtil {


    public static void afterHandleLog(Log sysLog, Object proceed, SysOperLog sysOperLog, int status, String errorMsg) {
        if (sysLog.isSaveResponseData()) {
            sysOperLog.setJsonResult(JSON.toJSONString(proceed));
        }
        sysOperLog.setStatus(status);
        sysOperLog.setErrorMsg(errorMsg);
    }


    public static void beforeHandleLog(Log sysLog, ProceedingJoinPoint joinPoint, SysOperLog sysOperLog) {
        sysOperLog.setTitle(sysLog.title());
        sysOperLog.setOperatorType(sysLog.operatorType().name());

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        sysOperLog.setMethod(method.getDeclaringClass().getName());

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        sysOperLog.setRequestMethod(request.getMethod());
        sysOperLog.setOperUrl(request.getRequestURI());
        sysOperLog.setOperIp(request.getRemoteAddr());

        if (sysLog.isSaveResponseData()) {
            String requestMethod = sysOperLog.getRequestMethod();
            if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
                String params = Arrays.toString(joinPoint.getArgs());
                sysOperLog.setOperParam(params);
            }
        }
        sysOperLog.setOperName(AuthContextUtil.get().getUserName());
    }

}
