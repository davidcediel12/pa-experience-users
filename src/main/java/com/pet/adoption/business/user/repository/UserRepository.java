package com.pet.adoption.business.user.repository;


import com.pet.adoption.business.user.dto.UserRequest;
import com.pet.adoption.business.user.util.JsonSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final JsonSerializer jsonSerializer;


    public String saveUser(UserRequest userRequest) {

        String userUuid = UUID.randomUUID().toString();

        redisTemplate.opsForHash().put(userUuid,
                "user", jsonSerializer.objectToJson(userRequest));

        return userUuid;
    }

}
