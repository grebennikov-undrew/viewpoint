package com.grebennikovas.viewpoint.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true) // никогда не возвращать пароль на фронт
    UserDto toDto(User user);

    User toUser(UserDto userDto);

}