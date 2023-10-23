package com.grebennikovas.viewpoint.users;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getUsername() {
        User user = new User();
        String username = "testuser";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    void setUsername() {
        User user = new User();
        String username = "testuser";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }
}