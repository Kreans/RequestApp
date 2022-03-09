package com.kurek.request_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class WrongStatusChange extends RuntimeException {

    public WrongStatusChange(String msg) {
        super(msg);
    }
}
