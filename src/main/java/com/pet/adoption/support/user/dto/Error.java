package com.pet.adoption.support.user.dto;

import java.util.List;

public record Error(String code, String message, List<String> detailedMessages) {
}
