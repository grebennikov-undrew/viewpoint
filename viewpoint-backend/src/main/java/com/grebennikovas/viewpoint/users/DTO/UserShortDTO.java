package com.grebennikovas.viewpoint.users.DTO;

import com.grebennikovas.viewpoint.users.User;

import java.util.Objects;

public class UserShortDTO {
    private Long id;
    private String username;

    public UserShortDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserShortDTO that = (UserShortDTO) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
