package com.usktea.plainold.exceptions;

public class InvalidRate extends RuntimeException {
    public InvalidRate() {
        super("값이 유효하지 않습니다");
    }
}
