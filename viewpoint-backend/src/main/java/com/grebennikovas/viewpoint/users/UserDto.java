package com.grebennikovas.viewpoint.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    private Boolean isActive;

}
