package com.usktea.plainold.exceptions;

public class InvalidUsername extends RuntimeException {
    public InvalidUsername() {
        super("유효하지 않은 유저이름입니다");
    }
}
