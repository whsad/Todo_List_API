package com.kirito.todoList.utils;


import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebUtils {

    public static void renderString(HttpServletResponse response, Object context){
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
