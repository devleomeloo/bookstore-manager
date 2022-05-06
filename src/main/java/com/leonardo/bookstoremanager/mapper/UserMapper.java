package com.leonardo.bookstoremanager.mapper;

import com.leonardo.bookstoremanager.dto.UserDTO;
import com.leonardo.bookstoremanager.entitys.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserDTO userDTO);

    UserDTO toDTO(User user);
}
