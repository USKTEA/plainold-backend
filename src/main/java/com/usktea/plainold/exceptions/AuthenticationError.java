package com.usktea.plainold.exceptions;

public class AuthenticationError extends RuntimeException {
    public AuthenticationError() {
        super("인증에 실패했습니다");
    }
}
