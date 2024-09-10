package com.pet.adoption.business.user.util;


import com.pet.adoption.business.user.dto.Error;
import com.pet.adoption.business.user.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorConstants {

    private ErrorConstants() {}

    public static final String GENERIC_CLIENT_MESSAGE = "Error while processing the information";

    public static final Error OBJECT_TO_JSON = new Error(
            "BS-99", GENERIC_CLIENT_MESSAGE, List.of());

    public static final ApiException OBJECT_TO_JSON_EXCEPTION =
            new ApiException(OBJECT_TO_JSON, HttpStatus.INTERNAL_SERVER_ERROR);


    public static final Error JSON_TO_OBJECT = new Error(
            "BS-98", GENERIC_CLIENT_MESSAGE, List.of());

    public static final ApiException JSON_TO_OBJECT_EXCEPTION =
            new ApiException(JSON_TO_OBJECT, HttpStatus.INTERNAL_SERVER_ERROR);



}
