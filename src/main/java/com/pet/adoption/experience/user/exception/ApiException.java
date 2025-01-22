package com.pet.adoption.experience.user.exception;


import com.pet.adoption.experience.user.dto.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final Error error;
    private final HttpStatus httpStatusCode;

    public ApiException(Error error, HttpStatus httpStatusCode) {
        super(error.message());
        this.error = error;
        this.httpStatusCode = httpStatusCode;
    }
}
