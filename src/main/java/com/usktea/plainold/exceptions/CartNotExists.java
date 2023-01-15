package com.usktea.plainold.exceptions;

public class CartNotExists extends RuntimeException {
    public CartNotExists() {
        super("유저의 장바구니가 존재하지 않습니다");
    }
}
