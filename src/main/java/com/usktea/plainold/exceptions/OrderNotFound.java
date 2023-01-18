package com.usktea.plainold.exceptions;

public class OrderNotFound extends RuntimeException {
    public OrderNotFound() {
        super("주문을 찾을 수 없습니다");
    }
}
