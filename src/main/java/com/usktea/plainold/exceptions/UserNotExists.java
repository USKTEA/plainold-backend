package com.usktea.plainold.exceptions;

public class UserNotExists extends RuntimeException {
    public UserNotExists() {
        super("유저가 존재하지 않습니다");
    }
}
