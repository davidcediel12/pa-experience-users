package com.pet.adoption.business.user.dto;

public record AuthenticationResponse(String version, Integer status, ResponseAction action,
                                     String userMessage, String userId) {

    public AuthenticationResponse(String version, Integer status, ResponseAction action,
                                  String userMessage){
        this(version, status, action, userMessage, null);
    }

}
