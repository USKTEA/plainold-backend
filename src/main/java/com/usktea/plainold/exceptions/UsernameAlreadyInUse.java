package com.usktea.plainold.exceptions;

public class UsernameAlreadyInUse extends RuntimeException {
    public UsernameAlreadyInUse() {
        super("이미 사용 중인 이메일입니다");
    }
}
