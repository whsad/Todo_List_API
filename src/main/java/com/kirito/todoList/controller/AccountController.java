package com.kirito.todoList.controller;

import com.kirito.todoList.annotaion.Limit;
import com.kirito.todoList.common.dtos.LoginDto;
import com.kirito.todoList.common.dtos.RegisterDto;
import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    @Limit(key = "register", permitsPerSecond = 50, timeout = 1000)
    public ResponseResult<?> register(@RequestBody RegisterDto dto) {
        return accountService.register(dto);
    }

    @PostMapping("/login")
    @Limit(key = "login", permitsPerSecond = 50, timeout = 1000)
    public ResponseResult<?> login(@RequestBody LoginDto dto){
        return accountService.login(dto);
    }

    @PostMapping("/logout")
    @Limit(key = "logout", permitsPerSecond = 50, timeout = 1000)
    public ResponseResult<?> logout(){
        return accountService.logout();
    }
}
