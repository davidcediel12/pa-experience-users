package com.pet.adoption.experience.user.mapper;

import com.pet.adoption.experience.user.dto.UserIdentity;
import com.pet.adoption.support.user.client.model.User;
import com.pet.adoption.experience.user.dto.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "identityId", source = "clientId")
    @Mapping(target = "name", source = "displayName")
    @Mapping(target = "issuerId", source = "identities", qualifiedByName = "identitiesToIssuerId")
    @Mapping(target = "issuerName", source = "identities", qualifiedByName = "identitiesToIssuerName")
    User toUser(UserRequest userRequest);

    @Named("identitiesToIssuerName")
    static String identitiesToIssuerName(List<UserIdentity> identities){
        return identities.isEmpty() ? "" : identities.getFirst().issuer();
    }

    @Named("identitiesToIssuerId")
    static String identitiesToIssuerId(List<UserIdentity> identities){
        return identities.isEmpty() ? "" : identities.getFirst().issuerAssignedId();
    }

}
