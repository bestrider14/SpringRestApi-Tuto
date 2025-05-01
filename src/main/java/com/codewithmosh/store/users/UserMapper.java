package com.codewithmosh.store.users;

import com.codewithmosh.store.users.dtos.RegisterUserRequest;
import com.codewithmosh.store.users.dtos.UpdateUserRequest;
import com.codewithmosh.store.users.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
