package com.kirito.todoList.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kirito.todoList.common.dtos.ItemDto;
import com.kirito.todoList.common.dtos.PageResult;
import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.common.enums.AppHttpCodeEnum;
import com.kirito.todoList.common.pojos.Todo;
import com.kirito.todoList.mapper.TodoMapper;
import com.kirito.todoList.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;


    /**
     * Create a To-Do Item
     */
    @PostMapping
    @PreAuthorize("hasAuthority('user')")
    public ResponseResult<?> create(@RequestBody ItemDto dto){
        return todoService.createItem(dto);
    }

    /**
     * Update a To-Do Item
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseResult<?> update(@PathVariable("id") String id, @RequestBody ItemDto dto){
        return todoService.updateItem(dto, id);
    }

    /**
     * Delete a To-Do Item
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseResult<?> delete(@PathVariable("id") String id){
        todoService.removeById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Get To-Do Items
     */
    @GetMapping
    @PreAuthorize("hasAuthority('user')")
    public ResponseResult<?> pageSize(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        IPage<Todo> iPage = new Page<>(page,limit);
        todoService.page(iPage, null);

        ResponseResult<Object> responseResult = new PageResult(page, limit, (int) iPage.getTotal());
        responseResult.setData(iPage.getRecords());
        return responseResult;
    }
}
