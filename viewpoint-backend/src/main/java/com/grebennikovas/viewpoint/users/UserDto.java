package com.grebennikovas.viewpoint.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String role;

    private Boolean isActive;

}
