package com.usktea.plainold.exceptions;

public class OrderNotBelongToUser extends RuntimeException {
    public OrderNotBelongToUser() {
        super("해당 권한이 없습니다");
    }
}
