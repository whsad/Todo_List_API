package com.kirito.todoList.utils;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class WebUtils {

    public static void renderString(HttpServletResponse response, Object context){
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JSON.toJSONString(context));
        } catch (IOException e) {
            log.warn("{}", e.getMessage());
        }
    }
}
