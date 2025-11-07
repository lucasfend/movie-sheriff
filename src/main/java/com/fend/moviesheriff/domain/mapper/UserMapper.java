package com.fend.moviesheriff.domain.mapper;

import com.fend.moviesheriff.domain.dto.userDTOs.CreateUserDTO;
import com.fend.moviesheriff.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(CreateUserDTO createUserDTO);
    CreateUserDTO toUserDTO(User user);
}
