package com.pet.adoption.support.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;

import static com.pet.adoption.support.user.constant.Constant.ROLE_PARAM_NAME;

public record UserRequest(AuthStep step,
                          @JsonProperty("client_id") String clientId,
                          @Email String email,
                          String displayName,
                          String country,
                          @JsonProperty(ROLE_PARAM_NAME)
                          String role) {

}
