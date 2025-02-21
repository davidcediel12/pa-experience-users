package com.pet.adoption.experience.user.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserIdentity(
        @NotEmpty String issuer,
        @NotEmpty String issuerAssignedId
) {
}
