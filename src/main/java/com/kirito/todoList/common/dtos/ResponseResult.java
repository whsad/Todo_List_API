package com.kirito.todoList.common.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kirito.todoList.common.enums.AppHttpCodeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String  info;
    private T data;

    public ResponseResult(){
        this.code = 200;
    }

    public ResponseResult<?> error(Integer code, String msg){
        this.code = code;
        this.info = msg;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data, String info){
        this.code = code;
        this.data = data;
        this.info = info;
        return this;
    }

    public static ResponseResult<?> okResult(int code, String msg){
        ResponseResult<?> result = new ResponseResult<>();
        return result.ok(code, null, msg);
    }

    public static ResponseResult<?> okResult(Object data){
        ResponseResult result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS, AppHttpCodeEnum.SUCCESS.getInfo());
        if (data != null){
            result.setData(data);
        }
        return result;
    }

    public static ResponseResult<?> okResult(AppHttpCodeEnum enums, Object data){
        ResponseResult result = setAppHttpCodeEnum(enums, enums.getInfo());
        if (data != null){
            result.setData(data);
        }
        return result;
    }

    public static ResponseResult<?> okResult(AppHttpCodeEnum enums){
        return setAppHttpCodeEnum(enums, enums.getInfo());
    }

    public static ResponseResult<?> errorResult(AppHttpCodeEnum enums){
        return setAppHttpCodeEnum(enums, enums.getInfo());
    }

    public static ResponseResult<?> errorResult(String info){
        return okResult(401, info);
    }

    private static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums, String info) {
        return okResult(enums.getCode(), info);
    }
}
