package com.kurek.request_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Cannot change content, request is not in CREATED or VERIFIED status")
public class ContentModificationException extends RuntimeException {
}
