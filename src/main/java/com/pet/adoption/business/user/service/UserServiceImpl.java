package com.pet.adoption.business.user.service;

import com.pet.adoption.business.user.client.api.UserApi;
import com.pet.adoption.business.user.client.model.CreateUserResponse;
import com.pet.adoption.business.user.client.model.User;
import com.pet.adoption.business.user.dto.UserRequest;
import com.pet.adoption.business.user.exception.ApiCallException;
import com.pet.adoption.business.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserApi userApi;
    private final UserMapper userMapper;

    @Override
    public URI createUser(UserRequest userRequest) {

        User user = userMapper.toUser(userRequest);
        log.info("Calling user api to save the user");

        try {
            ResponseEntity<CreateUserResponse> userResponse = userApi.addUserWithHttpInfo(user);
            log.info("User saved successfully");
            log.debug("Response {} END", userResponse.toString());

            return userResponse.getHeaders().getLocation();

        } catch (HttpClientErrorException e) {
            throw new ApiCallException("Support users", e);
        }

    }
}
