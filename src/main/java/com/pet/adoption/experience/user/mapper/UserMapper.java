package com.pet.adoption.experience.user.mapper;

import com.pet.adoption.experience.user.client.model.User;
import com.pet.adoption.experience.user.dto.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "identityId", source = "clientId")
    @Mapping(target = "name", source = "displayName")
    User toUser(UserRequest userRequest);
}
