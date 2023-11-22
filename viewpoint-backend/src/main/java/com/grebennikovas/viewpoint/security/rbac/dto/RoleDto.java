package com.grebennikovas.viewpoint.security.rbac.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grebennikovas.viewpoint.users.dto.UserDto;
import com.grebennikovas.viewpoint.users.dto.UserShortDto;
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

    private List<UserShortDto> users;

    private Set<PrivilegeDto> privileges;

}
