package com.usktea.plainold.exceptions;

public class NotHaveDeleteAnswerAuthority extends RuntimeException {
    public NotHaveDeleteAnswerAuthority() {
        super("해당 권한이 없습니다");
    }
}
