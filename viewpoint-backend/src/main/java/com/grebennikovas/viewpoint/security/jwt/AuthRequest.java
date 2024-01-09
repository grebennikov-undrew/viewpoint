package com.grebennikovas.viewpoint.security.jwt;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AuthRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
