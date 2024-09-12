package com.pet.adoption.business.user.service;

import com.pet.adoption.business.user.client.api.UserApi;
import com.pet.adoption.business.user.client.model.CreateUserResponse;
import com.pet.adoption.business.user.client.model.User;
import com.pet.adoption.business.user.dto.AuthStep;
import com.pet.adoption.business.user.dto.AuthenticationResponse;
import com.pet.adoption.business.user.dto.ResponseAction;
import com.pet.adoption.business.user.dto.UserRequest;
import com.pet.adoption.business.user.exception.ApiCallException;
import com.pet.adoption.business.user.mapper.UserMapper;
import com.pet.adoption.business.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserApi userApi;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Value("${spring.application.version}")
    private String version;

    @Override
    public ResponseEntity<AuthenticationResponse> createUser(UserRequest userRequest) {

        boolean isSignUp = userRequest.step().equals(AuthStep.POST_ATTRIBUTE_COLLECTION);

        if (isSignUp) {
            return saveNewUser(userRequest);
        }

        boolean isSignIn = userRequest.step().equals(AuthStep.PRE_TOKEN_ISSUANCE);

        if (isSignIn) {
            log.info("Saving user into cache");
            return saveExistentUserInCache(userRequest);
        }

        throw new IllegalStateException("Oops");
    }

    private ResponseEntity<AuthenticationResponse> saveExistentUserInCache(UserRequest userRequest) {
        String userId = userRepository.saveUser(userRequest);
        log.info("User saved into cache successfully");

        return ResponseEntity.ok()
                .body(new AuthenticationResponse(
                        version, HttpStatus.OK.value(),
                        ResponseAction.CONTINUE, "All right", userId));

    }


    private ResponseEntity<AuthenticationResponse> saveNewUser(UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        log.info("Calling user support api to save the user");

        try {
            ResponseEntity<CreateUserResponse> userResponse = userApi.addUserWithHttpInfo(user);
            log.info("User saved successfully");
            log.debug("Response {} END", userResponse.toString());

            return ResponseEntity.created(Objects.requireNonNull(userResponse.getHeaders().getLocation()))
                    .body(new AuthenticationResponse(
                            version, HttpStatus.OK.value(),
                            ResponseAction.CONTINUE, "All right"));

        } catch (HttpClientErrorException e) {
            throw new ApiCallException("Support users", e);
        }
    }
}
