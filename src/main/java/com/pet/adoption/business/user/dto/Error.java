package com.pet.adoption.business.user.dto;

import java.util.List;

public record Error(String code, String message, List<String> detailedMessages) {
}
