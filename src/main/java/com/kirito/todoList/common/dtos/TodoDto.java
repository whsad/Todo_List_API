package com.kirito.todoList.common.dtos;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class TodoDto {
    private String title;
    private String description;

    public boolean checkParam(){
        return StringUtils.isBlank(title) || StringUtils.isBlank(description);
    }
}
