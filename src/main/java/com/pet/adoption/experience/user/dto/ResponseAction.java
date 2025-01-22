package com.pet.adoption.experience.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseAction {
    CONTINUE("Continue"),
    SHOW_BLOCK_PAGE("ShowBlockPage"),
    VALIDATION_ERROR("ValidationError");

    private final String action;

    ResponseAction(String action){
        this.action = action;
    }

    @JsonValue
    public String getAction(){
        return this.action;
    }

    @JsonCreator
    public static ResponseAction fromValue(String value) {
        for (ResponseAction responseAction : values()) {
            String currentAuthStep = responseAction.getAction();
            if (currentAuthStep.equals(value)) {
                return responseAction;
            }
        }
        throw new IllegalArgumentException("Invalid Action: " + value);
    }
}
