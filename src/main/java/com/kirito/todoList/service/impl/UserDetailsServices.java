package com.kirito.todoList.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kirito.todoList.common.pojos.LoginUser;
import com.kirito.todoList.common.pojos.User;
import com.kirito.todoList.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserDetailsServices implements UserDetailsService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User user = accountMapper.selectOne(queryWrapper);

        // 如果没有查询到用户就抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("Invalid username or password");
        }

        // custom authority
        //List<String> list = new ArrayList<>(Arrays.asList("user", "admin"));
        List<String> list = new ArrayList<>(Collections.singletonList(user.getAuthority()));

        return new LoginUser(user, list);
    }


}
