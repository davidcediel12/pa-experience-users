package com.pet.adoption.support.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

public record UserRequest(AuthStep step,
                          @JsonProperty("client_id") String clientId,
                          @Pattern(regexp = "^a.*$")
                          String email,
                          String displayName,
                          String country) {

}
