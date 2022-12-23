package com.usktea.plainold.exceptions;

public class InvalidProductPrice extends RuntimeException {
    public InvalidProductPrice() {
        super("상품 금액은 0원 이하일 수 없습니다");
    }
}
