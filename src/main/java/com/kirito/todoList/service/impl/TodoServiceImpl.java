package com.kirito.todoList.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kirito.todoList.common.dtos.ItemDto;
import com.kirito.todoList.common.dtos.PageResult;
import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.common.pojos.LoginUser;
import com.kirito.todoList.common.pojos.Todo;
import com.kirito.todoList.mapper.TodoMapper;
import com.kirito.todoList.service.TodoService;
import com.kirito.todoList.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.kirito.todoList.common.enums.AppHttpCodeEnum.PARAMETER_ERROR;

@Service
@Slf4j
public class TodoServiceImpl extends ServiceImpl<TodoMapper, Todo> implements TodoService {

    @Autowired
    private TodoMapper todoMapper;

    @Autowired
    private RedisCache redisCache;

    private static volatile String cacheKey;

    @Override
    public ResponseResult<?> createItem(ItemDto dto) {
        if (dto.checkParam()) {
            return ResponseResult.errorResult(PARAMETER_ERROR);
        }

        Todo todo = new Todo(dto);
        todoMapper.insert(todo);
        redisCache.deleteObject(getCacheKey());
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
        redisCache.deleteObject(getCacheKey());
        return ResponseResult.okResult(todo);
    }

    @Override
    public ResponseResult<?> pageSize(Integer page, Integer limit) {
        ResponseResult<Object> result = redisCache.getCacheObject(getCacheKey());
        if (!Objects.isNull(result)){
            return result;
        }
        IPage<Todo> iPage = new Page<>(page,limit);
        todoMapper.selectPage(iPage, null);

        ResponseResult<Object> responseResult = new PageResult(page, limit, (int) iPage.getTotal());
        responseResult.setData(iPage.getRecords());
        redisCache.setCacheObject(getCacheKey(), responseResult);

        return responseResult;
    }

    @Override
    public String getCacheKey(){
        if (cacheKey != null){
            return cacheKey;
        }

        synchronized (TodoServiceImpl.class){
            if (cacheKey != null){
                return cacheKey;
            }
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            cacheKey = "Todo:pageSize:" + loginUser.getUser().getId();
            return cacheKey;
        }
    }
}
