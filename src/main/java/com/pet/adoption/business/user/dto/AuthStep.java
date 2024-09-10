package com.pet.adoption.business.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AuthStep {

    POST_ATTRIBUTE_COLLECTION("PostAttributeCollection"),
    POST_FEDERATION_SIGNUP("PostFederationSignup"),
    PRE_TOKEN_ISSUANCE("PreTokenIssuance");

    private final String step;

    AuthStep(String step){
        this.step = step;
    }

    @JsonValue
    public String getAuthStep(){
        return this.step;
    }

    @JsonCreator
    public static AuthStep fromValue(String value) {
        for (AuthStep authStep : values()) {
            String currentAuthStep = authStep.getAuthStep();
            if (currentAuthStep.equals(value)) {
                return authStep;
            }
        }
        throw new IllegalArgumentException("Invalid Auth Step: " + value);
    }

    @Override
    public String toString() {
        return this.step;
    }


}
