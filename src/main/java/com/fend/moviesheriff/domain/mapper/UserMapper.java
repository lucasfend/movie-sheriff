package com.fend.moviesheriff.domain.mapper;

import com.fend.moviesheriff.dto.UserDTO;
import com.fend.moviesheriff.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO userDTO);
}
