package com.usktea.plainold.exceptions;

public class GuestIsNotAuthorized extends RuntimeException {
    public GuestIsNotAuthorized() {
        super("해당 권한이 없습니다");
    }
}
