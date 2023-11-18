package com.grebennikovas.viewpoint.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grebennikovas.viewpoint.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonIgnore
    private String password;
    private boolean isActive;

    public User (Long id) {
        this.id = id;
    }
}
