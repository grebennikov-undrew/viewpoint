package com.grebennikovas.viewpoint.users;

public enum RoleLegacy {
    ADMIN ("admin"),
    ANALYST ("analyst"),
    PUBLIC ("public");

    private String role;

    RoleLegacy(String role) {
        this.role = role;
    }

    public String getStoredValue() {
        return role;
    }
}