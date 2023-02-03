package com.usktea.plainold.exceptions;

public class NotHaveEditAnswerAuthority extends RuntimeException {
    public NotHaveEditAnswerAuthority() {
        super("해당 권한이 없습니다");
    }
}
