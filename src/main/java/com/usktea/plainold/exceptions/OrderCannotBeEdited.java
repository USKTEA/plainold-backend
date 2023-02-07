package com.usktea.plainold.exceptions;

public class OrderCannotBeEdited extends RuntimeException {
    public OrderCannotBeEdited() {
        super("주문을 수정할 수 없습니다");
    }
}
