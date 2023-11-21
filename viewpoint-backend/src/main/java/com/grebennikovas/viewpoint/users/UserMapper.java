package com.grebennikovas.viewpoint.users;

import com.grebennikovas.viewpoint.security.rbac.Role;
import com.grebennikovas.viewpoint.security.rbac.dto.RoleDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true) // никогда не возвращать пароль на фронт
    @Mapping(target = "roles", source = "roles", qualifiedByName = "shortRoles")
    UserDto toDto(User user);

    User toUser(UserDto userDto);

    @Named("shortRoles")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "name")
    RoleDto rolesToDto(Role role);
}