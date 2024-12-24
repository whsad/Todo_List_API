package com.kirito.todoList.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kirito.todoList.common.dtos.ItemDto;
import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.common.pojos.Todo;
import com.kirito.todoList.mapper.TodoMapper;
import com.kirito.todoList.service.TodoService;
import javafx.util.Builder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.kirito.todoList.common.enums.AppHttpCodeEnum.PARAMETER_ERROR;

@Service
public class TodoServiceImpl extends ServiceImpl<TodoMapper, Todo> implements TodoService {

    @Autowired
    private TodoMapper todoMapper;

    @Override
    public ResponseResult<?> createItem(ItemDto dto) {
        if (dto.checkParam()) {
            return ResponseResult.errorResult(PARAMETER_ERROR);
        }

        Todo todo = new Todo(dto);
        todoMapper.insert(todo);
        return ResponseResult.okResult(todo);
    }

    @Override
    public ResponseResult<?> updateItem(ItemDto dto, String Id) {
        if (dto.checkParam() || StringUtils.isBlank(Id)){
            return ResponseResult.errorResult(PARAMETER_ERROR);
        }
        Todo todo = todoMapper.selectOne(Wrappers.<Todo>lambdaQuery().eq(Todo::getId, Id));
        if (Objects.isNull(todo)){
            return ResponseResult.errorResult(PARAMETER_ERROR);
        }

        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todoMapper.updateById(todo);
        return ResponseResult.okResult(todo);
    }
}
