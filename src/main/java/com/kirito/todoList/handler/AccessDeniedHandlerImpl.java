package com.kirito.todoList.handler;

import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.common.enums.AppHttpCodeEnum;
import com.kirito.todoList.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult<?> result = ResponseResult.errorResult(AppHttpCodeEnum.FORBIDDEN);
        WebUtils.renderString(response, result);
        log.warn("访问权限不足 {}, {}", result.getCode(), result.getInfo());
    }
}
