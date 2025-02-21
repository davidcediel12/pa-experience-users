package com.pet.adoption.experience.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pet.adoption.experience.user.constant.Constant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserRequest(AuthStep step,
                          @JsonProperty("client_id") String clientId,
                          @Email String email,
                          String displayName,
                          String country,
                          @JsonProperty(Constant.ROLE_PARAM_NAME)
                          String role,
                          String postalCode,
                          @NotEmpty List<UserIdentity> identities) {

}
