package com.usktea.plainold.exceptions;

public class NotHaveAuthorityToGetOrders extends RuntimeException {
    public NotHaveAuthorityToGetOrders() {
        super("해당 권한이 없습니다");
    }
}
