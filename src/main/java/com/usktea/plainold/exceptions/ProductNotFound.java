package com.usktea.plainold.exceptions;

public class ProductNotFound extends RuntimeException {
    public ProductNotFound() {
        super("존재하지 않는 상품입니다");
    }
}
