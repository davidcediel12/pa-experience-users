package com.pet.adoption.business.user.mapper;

import com.pet.adoption.business.user.client.model.User;
import com.pet.adoption.business.user.dto.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "identityId", source = "clientId")
    @Mapping(target = "name", source = "displayName")
    User toUser(UserRequest userRequest);
}
