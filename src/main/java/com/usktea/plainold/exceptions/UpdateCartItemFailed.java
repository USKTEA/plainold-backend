package com.usktea.plainold.exceptions;

public class UpdateCartItemFailed extends RuntimeException {
    public UpdateCartItemFailed() {
        super("장바구니 업데이트 실패");
    }
}
