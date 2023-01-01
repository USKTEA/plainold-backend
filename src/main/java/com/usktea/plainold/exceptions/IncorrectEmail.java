package com.usktea.plainold.exceptions;

public class IncorrectEmail extends RuntimeException {
    public IncorrectEmail() {
        super("잘못된 이메일입니다");
    }
}
