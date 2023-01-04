package com.usktea.plainold.exceptions;

public class EmptyOrderLines extends RuntimeException {
    public EmptyOrderLines() {
        super("주문 상품이 비어있습니다");
    }
}
