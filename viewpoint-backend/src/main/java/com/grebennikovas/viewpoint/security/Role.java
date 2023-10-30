package com.grebennikovas.viewpoint.security;

public enum Role {
    ADMIN ("admin"),
    ANALYST ("analyst"),
    PUBLIC ("public");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getStoredValue() {
        return role;
    }
}