package com.kirito.todoList.aspect;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.kirito.todoList.annotaion.Limit;
import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class LimitAop {
    /**
     * Different interfaces, different flow control
     * map key for Limiter.key
     */
    private final Map<String, RateLimiter> limiterMap = Maps.newConcurrentMap();

    @Around("@annotation(com.kirito.todoList.annotaion.Limit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // get limit command
        Limit limit = method.getAnnotation(Limit.class);
        if (limit != null){
            // key effect: different interfaces, different flow control
            String key = limit.key();
            RateLimiter rateLimiter = null;
            // Verify cache matches the key
            if (!limiterMap.containsKey(key)){
                // Create token bucket
                rateLimiter = RateLimiter.create(limit.permitsPerSecond());
                limiterMap.put(key, rateLimiter);
                log.info("create token bucket={}, capacity={}", key, limit.permitsPerSecond());
            }
            rateLimiter = limiterMap.get(key);
            // acquire token
            boolean acquire = rateLimiter.tryAcquire(limit.timeout(), limit.timeunit());
            // no token, return error prompt
            if (!acquire){
                log.debug("token bucket={}， get token fail", key);
                responseFail(limit.msg());
                return null;
            }
        }
        return joinPoint.proceed();
    }

    /**
     * return error prompt to front
     */
    private void responseFail(String msg){
        HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        WebUtils.renderString(response, ResponseResult.errorResult(msg));
    }
}
