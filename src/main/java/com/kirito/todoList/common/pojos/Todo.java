package com.kirito.todoList.common.pojos;

import com.kirito.todoList.common.dtos.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Todo implements Serializable {
    private String id;
    private String title;
    private String description;

    public Todo(ItemDto dto){
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.title = dto.getTitle();
        this.description = dto.getDescription();
    }
}
