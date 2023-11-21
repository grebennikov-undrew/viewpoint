package com.grebennikovas.viewpoint.security.rbac.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grebennikovas.viewpoint.security.rbac.Privilege;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.users.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {

    private Long id;

    private String name;

    private List<UserDto> users;

    private Set<PrivilegeDto> privileges;

}
