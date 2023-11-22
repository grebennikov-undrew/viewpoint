package com.grebennikovas.viewpoint.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grebennikovas.viewpoint.security.rbac.dto.RoleDto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @Min(1)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @Email
    private String email;

    private String password = "";

    private Set<RoleDto> roles;

    private Boolean isActive;

}
