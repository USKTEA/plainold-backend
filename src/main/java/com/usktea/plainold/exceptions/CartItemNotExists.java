package com.usktea.plainold.exceptions;

public class CartItemNotExists extends RuntimeException {
    public CartItemNotExists() {
        super("장바구니에 상품이 존재하지 않습니다");
    }
}
