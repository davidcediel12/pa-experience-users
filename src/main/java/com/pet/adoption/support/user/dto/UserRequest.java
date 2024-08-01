package com.pet.adoption.support.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;

public record UserRequest(AuthStep step,
                          @JsonProperty("client_id") String clientId,
                          @Email String email,
                          String displayName,
                          String country) {

}
