package com.kirito.todoList.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kirito.todoList.common.dtos.LoginDto;
import com.kirito.todoList.common.dtos.RegisterDto;
import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.common.pojos.User;

public interface AccountService extends IService<User> {

    ResponseResult<?> register(RegisterDto dto);

    ResponseResult<?> login(LoginDto dto);

    ResponseResult<?> logout();

}
