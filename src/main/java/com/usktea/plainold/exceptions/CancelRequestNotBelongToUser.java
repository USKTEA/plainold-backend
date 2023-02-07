package com.usktea.plainold.exceptions;

public class CancelRequestNotBelongToUser extends RuntimeException {
    public CancelRequestNotBelongToUser() {
        super("해당 권한이 없습니다");
    }
}
