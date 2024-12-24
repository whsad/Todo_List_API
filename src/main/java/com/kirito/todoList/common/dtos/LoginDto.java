package com.kirito.todoList.common.dtos;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

@Data
public class LoginDto {

    private String email;
    private String password;

    public boolean checkParam(){
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password))
            return true;
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return !Pattern.matches(emailRegex, email);
    }
}
