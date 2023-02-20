package com.usktea.plainold.exceptions;

public class CreateUserFailed extends RuntimeException {
    public CreateUserFailed(String message) {
        super(message);
    }
}
