package com.kirito.todoList.common.enums;

import lombok.Getter;

@Getter
public enum AppHttpCodeEnum {
    SUCCESS(200, "Operate Successfully"),
    PARAMETER_ERROR(400, "Parameter Error"),
    UNAUTHORIZED(401, "Unauthorized"),
    REGISTERED(402, "Email registered"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INVALID_SIGNATURE(501, "Invalid signature"),
    TOKEN_EXPIRATION(502, "token expiration"),
    ALGORITHM_ERROR(503, "The token algorithms are inconsistent"),
    ;

    final int code;
    final String info;

    AppHttpCodeEnum(int code, String info) {
        this.code = code;
        this.info = info;
    }
}
