package com.grebennikovas.viewpoint.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grebennikovas.viewpoint.BaseEntity;
import com.grebennikovas.viewpoint.BaseEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(value = BaseEntityListener.class)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private boolean isActive;

    public User (Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
