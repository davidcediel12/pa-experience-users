package com.pet.adoption.business.user.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.pet.adoption.business.user.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@RestControllerAdvice
public class UserControllerAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Error> handleValidationError(MethodArgumentNotValidException e) {

        List<String> errorMessages = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ":" +
                        fieldError.getDefaultMessage())
                .toList();


        return ResponseEntity.badRequest()
                .body(new Error("BS-01", "Wrong parameters", errorMessages));
    }


    @ExceptionHandler({HttpMessageNotReadableException.class, ValueInstantiationException.class})
    private ResponseEntity<Error> handleValidationEnumException(HttpMessageNotReadableException e) {

        if (e.getCause() instanceof InvalidFormatException formatException) {
            if (formatException.getTargetType() != null && formatException.getTargetType().isEnum()) {
                return ResponseEntity
                        .badRequest()
                        .body(new Error("BS-02", "Wrong step",
                                List.of(formatException.getValue().toString())));
            }
        } else if (e.getCause() instanceof ValueInstantiationException instanceException &&
                instanceException.getType() != null && instanceException.getType().isEnumType()) {
            return ResponseEntity
                    .badRequest()
                    .body(new Error("BS-02", "Wrong step",
                            List.of(instanceException.getOriginalMessage())));

        }

        throw e;
    }


    @ExceptionHandler(ResourceAccessException.class)
    private ResponseEntity<Error> handleResourceAccessException(ResourceAccessException e) {

        var error = new Error("BS-03", "Couldn't obtain the information", List.of(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }
}
