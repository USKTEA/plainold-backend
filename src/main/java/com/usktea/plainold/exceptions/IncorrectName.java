package com.usktea.plainold.exceptions;

public class IncorrectName extends RuntimeException {
    public IncorrectName() {
        super("잘못된 이름입니다");
    }
}
