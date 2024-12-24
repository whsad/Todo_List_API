package com.kirito.todoList.controller;


import com.kirito.todoList.annotaion.Limit;
import com.kirito.todoList.common.dtos.ItemDto;
import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.common.enums.AppHttpCodeEnum;
import com.kirito.todoList.service.TodoService;
import com.kirito.todoList.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private RedisCache redisCache;

    /**
     * Create a To-Do Item
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    @Limit(key = "create", permitsPerSecond = 30, timeout = 1000)
    public ResponseResult<?> create(@RequestBody ItemDto dto){
        return todoService.createItem(dto);
    }

    /**
     * Update a To-Do Item
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    @Limit(key = "update", permitsPerSecond = 30, timeout = 1000)
    public ResponseResult<?> update(@PathVariable("id") String id, @RequestBody ItemDto dto){
        return todoService.updateItem(dto, id);
    }

    /**
     * Delete a To-Do Item
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    @Limit(key = "delete", permitsPerSecond = 30, timeout = 1000)
    public ResponseResult<?> delete(@PathVariable("id") String id){
        todoService.removeById(id);
        redisCache.deleteObject(todoService.getCacheKey());
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Get To-Do Items
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    @Limit(key = "pageSize", permitsPerSecond = 30, timeout = 1000)
    public ResponseResult<?> pageSize(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        return todoService.pageSize(page,limit);
    }
}
