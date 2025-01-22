package com.pet.adoption.experience.user.dto;

import java.util.List;

public record Error(String code, String message, List<String> detailedMessages) {
}
