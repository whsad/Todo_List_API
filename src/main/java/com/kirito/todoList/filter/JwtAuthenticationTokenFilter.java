package com.kirito.todoList.filter;


import com.kirito.todoList.common.pojos.LoginUser;
import com.kirito.todoList.utils.JwtUtils;
import com.kirito.todoList.utils.RedisCache;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("Authorization");

        // 检查 Token 是否存在并解析
        if (!StringUtils.hasText(token)) {
            // 放行，交给后续处理
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token
        String userId;
        try {
            userId = JwtUtils.parseJWT(token).getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 从 redis 中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录");
        }
        // 存入 SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities())
        );
        // 放行
        filterChain.doFilter(request, response);
    }
}
