package com.pet.adoption.business.user.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.pet.adoption.business.user.dto.AuthStep;
import com.pet.adoption.business.user.dto.UserRequest;
import com.redis.testcontainers.RedisContainer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.utility.DockerImageName;

import java.util.Objects;
import java.util.Set;

import static com.pet.adoption.business.user.util.Constants.USER_REQUEST;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(ids = "com.per.adoption:support.users:+:8081",
        repositoryRoot = "git://https://github.com/davidcediel12/pet-adoption-contracts.git",
        stubsMode = StubRunnerProperties.StubsMode.REMOTE,
        properties = {"git.branch=main"})
class UserControllerIntegrationTest {

    static RedisContainer redis = new RedisContainer(DockerImageName.parse("redis:7-alpine"));

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @LocalServerPort
    private Integer port;

    @BeforeAll
    static void setUp() {
        redis.start();
    }

    @AfterAll
    static void tearDown() {
        redis.stop();
    }

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:" + port;
        Objects.requireNonNull(redisTemplate.keys("*")).forEach(redisTemplate::delete);
    }


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getRedisHost);
        registry.add("spring.data.redis.port", redis::getRedisPort);
    }


    @Test
    void shouldSaveUserInCacheWhenSignInAttempt() {

        UserRequest userRequest = new UserRequest(AuthStep.PRE_TOKEN_ISSUANCE,
                USER_REQUEST.clientId(), USER_REQUEST.email(), USER_REQUEST.displayName(), USER_REQUEST.country(),
                USER_REQUEST.role(), USER_REQUEST.postalCode());

        given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.OK.value());


        Set<String> keys = redisTemplate.keys("*");
        assertThat(keys).hasSize(1);

        String key = keys.iterator().next();

        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String user = hashOperations.get(key, "user");

        DocumentContext savedUser = JsonPath.parse(user);

        String userName = savedUser.read("$.displayName");

        assertThat(userName).isEqualTo(USER_REQUEST.displayName());
    }

    @Test
    void shouldReturnCreatedUserIdWhenSignInAttempt() {

        UserRequest userRequest = new UserRequest(AuthStep.PRE_TOKEN_ISSUANCE,
                USER_REQUEST.clientId(), USER_REQUEST.email(), USER_REQUEST.displayName(), USER_REQUEST.country(),
                USER_REQUEST.role(), USER_REQUEST.postalCode());

        given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("extension_userId", notNullValue());
    }



    @Test
    void shouldSaveUserInDatabaseWhenSignUpAttempt() {
        given()
                .contentType(ContentType.JSON)
                .body(USER_REQUEST)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        assertThat(redisTemplate.keys("*")).isEmpty();
    }


}