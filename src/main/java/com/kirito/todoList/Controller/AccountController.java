package com.kirito.todoList.Controller;

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
    public ResponseResult<?> register(@RequestBody RegisterDto dto) {
        return accountService.register(dto);
    }

    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody LoginDto dto){
        return accountService.login(dto);
    }

    @PostMapping("/logout")
    public ResponseResult<?> logout(){
        return accountService.logout();
    }
}
