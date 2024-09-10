package com.pet.adoption.business.user.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.pet.adoption.business.user.dto.AuthStep;
import com.pet.adoption.business.user.dto.UserRequest;
import com.pet.adoption.business.user.exception.ApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.InputStream;

import static com.pet.adoption.business.user.util.Constants.USER_REQUEST;
import static com.pet.adoption.business.user.util.Constants.USER_REQUEST_JSON;
import static com.pet.adoption.business.user.util.ErrorConstants.JSON_TO_OBJECT_EXCEPTION;
import static com.pet.adoption.business.user.util.ErrorConstants.OBJECT_TO_JSON_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ObjectMapper.class, JsonSerializer.class})
class JsonSerializerTest {


    @Autowired
    JsonSerializer jsonSerializer;

    @Test
    void shouldConvertToStringCorrectly() {

        String convertedUser = jsonSerializer.objectToJson(USER_REQUEST);

        DocumentContext documentContext = JsonPath.parse(convertedUser);

        String name = documentContext.read("$.displayName");
        assertThat(name).isEqualTo("user name");

        String authStep = documentContext.read("$.step");
        assertThat(authStep).isEqualTo(AuthStep.POST_ATTRIBUTE_COLLECTION.toString());
    }


    @Test
    void shouldThrowCorrectErrorWhenSerializationFails() {
        UnserializableObject unserializableObject = new UnserializableObject();
        ApiException exception = Assertions.assertThrows(ApiException.class,
                () -> jsonSerializer.objectToJson(unserializableObject));

        assertEquals(OBJECT_TO_JSON_EXCEPTION, exception);
    }

    @Test
    void shouldDeserializeCorrectly(){
        UserRequest userRequest = jsonSerializer.jsonToObject(USER_REQUEST_JSON, UserRequest.class);
        assertEquals(USER_REQUEST, userRequest);
    }


    @Test
    void shouldThrowCorrectErrorWhenDeSerializationFails() {
        ApiException exception = Assertions.assertThrows(ApiException.class,
                () -> jsonSerializer.jsonToObject("}{", UserRequest.class));

        assertEquals(JSON_TO_OBJECT_EXCEPTION, exception);
    }

    static class UnserializableObject{
        InputStream inputStream;

        public UnserializableObject() { /* empty constructor just to test */ }

    }
}