package com.coffee.export.fullstack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/26/23
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RequestValidationException extends RuntimeException {
    public RequestValidationException(String message) {
        super(message);
    }

}
