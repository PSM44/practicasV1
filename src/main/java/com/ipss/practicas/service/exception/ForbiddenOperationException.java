package com.ipss.practicas.service.exception;

public class ForbiddenOperationException extends RuntimeException {
    public ForbiddenOperationException(String msg) { super(msg); }
}
