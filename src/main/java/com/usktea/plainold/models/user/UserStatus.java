package com.usktea.plainold.models.user;

public enum UserStatus {
    ACTIVE("Active"),
    DELETED("Deleted");

    private String status;

    UserStatus(String status) {
        this.status = status;
    }
}
