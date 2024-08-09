package com.pet.adoption.business.user.service;

import com.pet.adoption.business.user.dto.UserRequest;

import java.net.URI;

public interface UserService {

    URI createUser(UserRequest userRequest);
}
