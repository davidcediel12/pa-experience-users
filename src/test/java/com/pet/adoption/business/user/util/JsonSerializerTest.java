package com.pet.adoption.business.user.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.pet.adoption.business.user.dto.AuthStep;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.pet.adoption.business.user.util.Constants.USER_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;


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
}