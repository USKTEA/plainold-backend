package com.usktea.plainold.exceptions;

public class OrderCanWriteReviewNotFound extends RuntimeException {
    public OrderCanWriteReviewNotFound() {
        super("해당하는 주문기록을 찾지 못했습니다");
    }
}
