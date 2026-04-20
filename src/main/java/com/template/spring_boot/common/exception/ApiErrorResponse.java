package com.template.spring_boot.common.exception;

public record ApiErrorResponse(
    int code,
    String message
) {
}