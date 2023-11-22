package com.grebennikovas.viewpoint.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grebennikovas.viewpoint.security.rbac.dto.RoleDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserShortDto {

    @Min(1)
    private Long id;
    private String username;


}