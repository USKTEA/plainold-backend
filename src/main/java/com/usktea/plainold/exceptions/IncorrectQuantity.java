package com.usktea.plainold.exceptions;

public class IncorrectQuantity extends RuntimeException {
    public IncorrectQuantity() {
        super("잘못된 수량입니다");
    }
}
