package com.pet.adoption.business.user.service;

import com.pet.adoption.business.user.client.api.UserApi;
import com.pet.adoption.business.user.client.model.CreateUserResponse;
import com.pet.adoption.business.user.client.model.User;
import com.pet.adoption.business.user.dto.AuthStep;
import com.pet.adoption.business.user.dto.UserRequest;
import com.pet.adoption.business.user.exception.ApiCallException;
import com.pet.adoption.business.user.mapper.UserMapper;
import com.pet.adoption.business.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public URI createUser(UserRequest userRequest) {

        boolean isSignUp = userRequest.step().equals(AuthStep.POST_ATTRIBUTE_COLLECTION);

        if(isSignUp){
            return saveNewUser(userRequest);
        }

        boolean isSignIn = userRequest.step().equals(AuthStep.PRE_TOKEN_ISSUANCE);

        if(isSignIn){
            return saveExistentUserInCache(userRequest);
        }

        throw new IllegalStateException("Oops");
    }

    private URI saveExistentUserInCache(UserRequest userRequest) {
        return URI.create(userRepository.saveUser(userRequest));
    }


    private URI saveNewUser(UserRequest userRequest) {
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
