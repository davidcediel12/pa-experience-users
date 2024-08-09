package com.pet.adoption.business.user.config;

import com.pet.adoption.business.user.client.ApiClient;
import com.pet.adoption.business.user.client.api.UserApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class ApiConfig {

    @Value("${api.support-user.base-path}")
    private String supportUserBasePath;

    @Bean
    ApiClient apiClient(RestClient.Builder builder) {
        ApiClient apiClient = new ApiClient(builder.build());
        apiClient.setBasePath(supportUserBasePath);
        apiClient.addDefaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        apiClient.addDefaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return apiClient;
    }

    @Bean
    UserApi userApi(ApiClient apiClient) {
        return new UserApi(apiClient);
    }
}
