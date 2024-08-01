package com.pet.adoption.support.user.controller;

import com.pet.adoption.support.user.dto.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {


    @PostMapping
    public ResponseEntity<String> createUser(@RequestHeader Map<String, String> headers,
                                              @Valid @RequestBody UserRequest user){

        System.out.println(headers);
        System.out.println(user);
        return ResponseEntity.ok(":)");

    }
}
