package com.rapipay.wrapperutility.status;

import org.springframework.http.HttpStatus;

public enum ApiStatusCodes {

    NULL_POINTER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), "NullPointerException"),

    NUMBER_FORMAT_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), "NumberFormatException");
    private final int code;
    private final String message;


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ApiStatusCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
