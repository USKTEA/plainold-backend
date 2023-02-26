package com.usktea.plainold.exceptions;

public class UserAlreadyLikedProduct extends RuntimeException {
    public UserAlreadyLikedProduct() {
        super("좋아요는 상품 당 한 번만 할 수 있습니다");
    }
}
