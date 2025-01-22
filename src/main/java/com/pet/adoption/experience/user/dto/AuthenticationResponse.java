package com.pet.adoption.experience.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(String version,
                                     Integer status, ResponseAction action,
                                     String userMessage,
                                     @JsonProperty(value = "extension_userId", access = JsonProperty.Access.READ_ONLY)
                                     String userId) {



    public AuthenticationResponse(String version, Integer status, ResponseAction action,
                                  String userMessage){
        this(version, status, action, userMessage, null);
    }

}
