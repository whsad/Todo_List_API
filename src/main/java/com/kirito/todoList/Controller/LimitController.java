package com.kirito.todoList.Controller;

import com.kirito.todoList.annotaion.Limit;
import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/limit")
public class LimitController {

    @GetMapping("/test2")
    @Limit(key = "limit2", permitsPerSecond = 1, timeout = 500, msg = "当前人数较多, 请稍后再试")
    public ResponseResult<?> limit2(){
        log.info("token bucket limit2 get token successfully");
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @GetMapping("/test3")
    @Limit(key = "limit3", permitsPerSecond = 3, timeout = 500, msg = "系统繁忙, 稍后再试!")
    public ResponseResult<?> limit3(){
        log.info("token bucket limit3 get token successfully");
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

}
