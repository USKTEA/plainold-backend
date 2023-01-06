package com.usktea.plainold.exceptions;

public class InvalidProductOption extends RuntimeException {
    public InvalidProductOption() {
        super("유효하지 않은 상품 옵션입니다");
    }
}
