package com.jwt.security.utils.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_EXISTED(409, "User already exists", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(409, "Role already exists", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(409, "Permission already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(404, "User not found", HttpStatus.NOT_FOUND),
    ID_NOT_FOUND(404, "Id not found", HttpStatus.NOT_FOUND),
    NAME_NOT_FOUND(404, "Name not found", HttpStatus.NOT_FOUND),
    PASSWORD_INCORRECT(400, "Password is incorrect", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(404, "Role not found", HttpStatus.NOT_FOUND),
    TOKEN_INVALID(401, "Invalid token", HttpStatus.FORBIDDEN),
    USER_NOT_ACTIVE(401, "User is not active", HttpStatus.UNAUTHORIZED),;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
