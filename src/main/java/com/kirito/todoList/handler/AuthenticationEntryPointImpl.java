package com.kirito.todoList.handler;

import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.kirito.todoList.common.enums.AppHttpCodeEnum.UNAUTHORIZED;

@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 设置响应状态和类型
        ResponseResult<?> result = ResponseResult.errorResult(UNAUTHORIZED);
        WebUtils.renderString(response, result);
        log.warn("用户认证失败 {}, {}", result.getCode(), result.getInfo());

    }
}
