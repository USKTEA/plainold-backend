package com.usktea.plainold.exceptions;

public class IncorrectZipCode extends RuntimeException {
    public IncorrectZipCode() {
        super("잘못된 우편번호입니다");
    }
}
