package com.pet.adoption.business.user.controller;

import com.pet.adoption.business.user.dto.AuthenticationResponse;
import com.pet.adoption.business.user.dto.ResponseAction;
import com.pet.adoption.business.user.dto.UserRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${spring.application.version}")
    private String version;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> createUser(@RequestHeader Map<String, String> headers,
                                                             @Valid @RequestBody UserRequest user){

        System.out.println(headers);
        System.out.println(user);
        return ResponseEntity.ok(new AuthenticationResponse(
                version, HttpStatus.OK.value(), ResponseAction.CONTINUE, "All right"));

    }
}
