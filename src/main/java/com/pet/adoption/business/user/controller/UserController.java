package com.pet.adoption.business.user.controller;

import com.pet.adoption.business.user.dto.AuthenticationResponse;
import com.pet.adoption.business.user.dto.UserRequest;
import com.pet.adoption.business.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> createUser(@RequestHeader Map<String, String> headers,
                                                             @Valid @RequestBody UserRequest user) {

        log.info("Received new message after action {} of the user {}", user.step(), user.displayName());
        log.debug(headers.toString());
        log.debug(user.toString());

        return userService.createUser(user);

    }
}
