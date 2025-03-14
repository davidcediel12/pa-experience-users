package com.pet.adoption.experience.user.util;

import com.pet.adoption.experience.user.dto.AuthStep;
import com.pet.adoption.experience.user.dto.UserIdentity;
import com.pet.adoption.experience.user.dto.UserRequest;

import java.util.List;

public class Constants {

    private Constants() {
    }


    public static final String GENERIC_UUID = "115de516-ae8a-4db5-b63b-969c6696a848";

    public static final UserRequest USER_REQUEST = new UserRequest(
            AuthStep.POST_ATTRIBUTE_COLLECTION, GENERIC_UUID,
            "user@email.com", "user name", "Spain", "USER", "298271",
            List.of(new UserIdentity("google", "google12")));

    public static final String USER_REQUEST_JSON = """
                {
                    "step": "PostAttributeCollection",
                    "client_id": "115de516-ae8a-4db5-b63b-969c6696a848",
                    "email": "user@email.com",
                    "displayName": "user name",
                    "country": "Spain",
                    "extension_cb77353b732548a7a6f3ef6eef4401e9_Role": "USER",
                    "postalCode": "298271"
                }
            """;
}
