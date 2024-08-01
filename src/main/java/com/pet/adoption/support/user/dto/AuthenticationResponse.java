package com.pet.adoption.support.user.dto;

public record AuthenticationResponse(String version, Integer status, ResponseAction action,
                                     String userMessage) {


}
