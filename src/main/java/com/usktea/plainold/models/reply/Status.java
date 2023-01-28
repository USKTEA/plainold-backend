package com.usktea.plainold.models.reply;

public enum Status {
    ACTIVE("ACTIVE"),
    DELETED("DELETED");

    private String status;

    Status(String status) {
        this.status = status;
    }
}
