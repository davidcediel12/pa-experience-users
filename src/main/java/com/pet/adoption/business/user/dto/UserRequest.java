package com.pet.adoption.business.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pet.adoption.business.user.constant.Constant;
import jakarta.validation.constraints.Email;

public record UserRequest(AuthStep step,
                          @JsonProperty("client_id") String clientId,
                          @Email String email,
                          String displayName,
                          String country,
                          @JsonProperty(Constant.ROLE_PARAM_NAME)
                          String role,
                          String postalCode) {

}
