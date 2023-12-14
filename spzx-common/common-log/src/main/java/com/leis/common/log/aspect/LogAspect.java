package com.leis.common.log.aspect;

import com.leis.common.log.annotation.Log;
import com.leis.common.log.service.IAsyncOperLogService;
import com.leis.common.log.util.LogUtil;
import com.leis.model.entity.system.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private IAsyncOperLogService asyncOperLogService;

    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog) {

        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog, joinPoint, sysOperLog);

        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            LogUtil.afterHandleLog(sysLog, proceed, sysOperLog, 0, null);
        } catch (Throwable e) {
            e.printStackTrace();
            LogUtil.afterHandleLog(sysLog, proceed, sysOperLog, 1, e.getMessage());
            throw new RuntimeException();
        }
        return proceed;
    }

}
