package com.senla.project.utils.mapper;

import com.senla.project.models.DTO.requests.UserLoginRequest;
import com.senla.project.models.DTO.requests.UserRequest;
import com.senla.project.models.DTO.responses.UserResponse;
import com.senla.project.models.User;
import com.senla.project.utils.hash.HashUtil;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", imports = {HashUtil.class})
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", expression = "java(HashUtil.encode(userLoginRequest.getPassword()))")
    User loginToEntity(UserLoginRequest userLoginRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", expression = "java(HashUtil.encode(userRequest.getPassword()))")
    User toEntity(UserRequest userRequest);

    UserResponse toDto(User user);

    List<UserResponse> toDtoList(List<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", expression = "java(HashUtil.encode(userRequest.getPassword()))")
    void updateEntity(UserRequest userRequest, @MappingTarget User user);
}
