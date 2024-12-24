package com.kirito.todoList.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kirito.todoList.common.dtos.LoginDto;
import com.kirito.todoList.common.dtos.RegisterDto;
import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.common.enums.AppHttpCodeEnum;
import com.kirito.todoList.common.pojos.LoginUser;
import com.kirito.todoList.common.pojos.User;
import com.kirito.todoList.mapper.AccountMapper;
import com.kirito.todoList.service.AccountService;
import com.kirito.todoList.utils.JwtUtils;
import com.kirito.todoList.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.kirito.todoList.common.enums.AppHttpCodeEnum.*;


@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, User> implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseResult<?> register(RegisterDto dto) {
        if (dto.checkParam()) {
            return ResponseResult.errorResult(PARAMETER_ERROR);
        }
        String encode = passwordEncoder.encode(dto.getPassword());
        System.out.println(encode);
        boolean matches = passwordEncoder.matches(dto.getPassword(), encode);
        System.out.println(matches);
        dto.setPassword(encode);

        User user = accountMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, dto.getEmail()));
        if (user != null) {
            return ResponseResult.errorResult(REGISTERED);
        }
        user = new User(dto);
        accountMapper.insert(user);
        String userId = user.getId();
        String jwt = JwtUtils.createJWT(userId);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        List<String> list = new ArrayList<>(Arrays.asList("user", "admin"));
        redisCache.setCacheObject("login:" + userId, new LoginUser(user, list));

        return ResponseResult.okResult(map);
    }

    public ResponseResult<?> login(LoginDto dto) {
        // 1. 检查参数
        if (dto.checkParam()) {
            return ResponseResult.errorResult(PARAMETER_ERROR);
        }
        String email = dto.getEmail();
        String password = dto.getPassword();

        // 2.校验登录
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("Login fail");
        }

        // 3. 生成Jwt令牌
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId();
        String jwt = JwtUtils.createJWT(userId);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        redisCache.setCacheObject("login:" + userId, loginUser);

        // 4. 返回
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult<?> logout() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId();
        // 删除 redis 中的值
        redisCache.deleteObject("login:" + userId);
        return ResponseResult.okResult(SUCCESS);
    }
}
