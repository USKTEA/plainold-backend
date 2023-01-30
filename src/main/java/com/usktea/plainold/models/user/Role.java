package com.usktea.plainold.models.user;

public enum Role {
    GUEST("Guest"),
    MEMBER("Member"),
    ADMIN("Admin");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return this.role.equals("Admin");
    }
}
