package com.kirito.todoList.filter;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class PreRateLimitFilter extends OncePerRequestFilter {

    // global flow control: 100 requests per second
    private final RateLimiter rateLimiter = RateLimiter.create(100);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!rateLimiter.tryAcquire(500, TimeUnit.MILLISECONDS)){
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            WebUtils.renderString(response, ResponseResult.errorResult("Rate limit exceeded"));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
