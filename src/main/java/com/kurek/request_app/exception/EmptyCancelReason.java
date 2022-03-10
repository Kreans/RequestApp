package com.kurek.request_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Cancel reason cannot be null")
public class EmptyCancelReason extends RuntimeException {
}
