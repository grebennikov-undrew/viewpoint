package com.grebennikovas.viewpoint.users;

import com.grebennikovas.viewpoint.security.rbac.Role;
import com.grebennikovas.viewpoint.security.rbac.dto.RoleDto;
import com.grebennikovas.viewpoint.users.dto.UserDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

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