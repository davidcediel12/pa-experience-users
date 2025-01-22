package com.pet.adoption.experience.user.service;

import com.pet.adoption.experience.user.dto.AuthenticationResponse;
import com.pet.adoption.experience.user.dto.UserRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<AuthenticationResponse> createUser(UserRequest userRequest);
}
