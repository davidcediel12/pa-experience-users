package com.pet.adoption.support.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AuthStep {

    POST_ATTRIBUTE_COLLECTION("PostAttributeCollection"),
    POST_FEDERATION_SIGNUP("PostFederationSignup"),
    PRE_TOKEN_ISSUANCE("PreTokenIssuance");

    private final String step;

    AuthStep(String step){
        this.step = step;
    }

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


}
