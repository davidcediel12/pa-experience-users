package com.pet.adoption.business.user.exception;

import lombok.Getter;

@Getter
public class ApiCallException extends RuntimeException {

    private final String calledApi;

    public ApiCallException(String calledApi, Throwable cause) {
        super(cause);
        this.calledApi = calledApi;
    }
}
