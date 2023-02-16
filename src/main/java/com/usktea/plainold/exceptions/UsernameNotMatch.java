package com.usktea.plainold.exceptions;

public class UsernameNotMatch extends RuntimeException {
    public UsernameNotMatch() {
        super("정보가 맞지 않습니다");
    }
}
