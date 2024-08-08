package com.pet.adoption.business.user.config;

import com.pet.adoption.business.user.client.ApiClient;
import com.pet.adoption.business.user.client.api.UserApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ApiConfig {

    @Value("${api.support-user.base-path}")
    private String supportUserBasePath;

    @Bean
    ApiClient apiClient(RestClient.Builder builder) {
        ApiClient apiClient = new ApiClient(builder.build());
        apiClient.setBasePath(supportUserBasePath);
        return apiClient;
    }

    @Bean
    UserApi userApi(ApiClient apiClient) {
        return new UserApi(apiClient);
    }
}
