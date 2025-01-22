package com.pet.adoption.experience.user.repository;


import com.pet.adoption.experience.user.dto.UserRequest;
import com.pet.adoption.experience.user.util.JsonSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final JsonSerializer jsonSerializer;
    @Value("${spring.data.redis.lifetime-minutes}")
    private Integer cacheLifeTime;


    public String saveUser(UserRequest userRequest) {

        String userUuid = UUID.randomUUID().toString();

        redisTemplate.opsForHash().put(userUuid,
                "user", jsonSerializer.objectToJson(userRequest));

        redisTemplate.expire(userUuid, Duration.ofMinutes(cacheLifeTime));

        return userUuid;
    }

}
