package com.kirito.todoList.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.kirito.todoList.common.dtos.ItemDto;
import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.common.pojos.Todo;

public interface TodoService extends IService<Todo> {
    ResponseResult<?> createItem(ItemDto dto);

    ResponseResult<?> updateItem(ItemDto dto, String Id);
}
