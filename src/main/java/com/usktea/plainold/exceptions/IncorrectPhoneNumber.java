package com.usktea.plainold.exceptions;

public class IncorrectPhoneNumber extends RuntimeException {
    public IncorrectPhoneNumber() {
        super("잘못된 핸드폰 번호입니다");
    }
}
