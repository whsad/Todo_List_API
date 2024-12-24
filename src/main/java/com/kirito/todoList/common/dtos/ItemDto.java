package com.kirito.todoList.common.dtos;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class ItemDto {
    private String title;
    private String description;

    public boolean checkParam(){
        if (StringUtils.isBlank(title) || StringUtils.isBlank(description)){
            return true;
        }
        return false;
    }
}
