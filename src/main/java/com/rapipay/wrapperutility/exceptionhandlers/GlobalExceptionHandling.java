package com.rapipay.wrapperutility.exceptionhandlers;

import com.rapipay.wrapperutility.response.ApiResponse;
import com.rapipay.wrapperutility.status.ApiStatusCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse> nullPointerException(NullPointerException ex) {

        return new ResponseEntity<>(new ApiResponse(
                ApiStatusCodes.NULL_POINTER_EXCEPTION.getCode(),
                ApiStatusCodes.NULL_POINTER_EXCEPTION.getMessage())
                , HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiResponse> numberFormatException(NumberFormatException ex) {
        return new ResponseEntity<>(new ApiResponse(
                ApiStatusCodes.NUMBER_FORMAT_EXCEPTION.getCode(),
                ApiStatusCodes.NUMBER_FORMAT_EXCEPTION.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
