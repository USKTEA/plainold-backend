package com.usktea.plainold.exceptions;

public class InvalidPrice extends RuntimeException {
    public InvalidPrice() {
        super("비정상적인 금액입니다");
    }
}
