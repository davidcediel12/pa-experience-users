package com.pet.adoption.business.user.controller;

import com.pet.adoption.business.user.dto.AuthenticationResponse;
import com.pet.adoption.business.user.dto.ResponseAction;
import com.pet.adoption.business.user.dto.UserRequest;
import com.pet.adoption.business.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @Value("${spring.application.version}")
    private String version;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> createUser(@RequestHeader Map<String, String> headers,
                                                             @Valid @RequestBody UserRequest user){

        userService.createUser(user);

        log.debug(headers.toString());
        log.debug(user.toString());
        
        return ResponseEntity.ok(new AuthenticationResponse(
                version, HttpStatus.OK.value(), ResponseAction.CONTINUE, "All right"));

    }
}
