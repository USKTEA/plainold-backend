package com.usktea.plainold.exceptions;

public class NotHaveCreateAnswerAuthority extends RuntimeException {
    public NotHaveCreateAnswerAuthority() {
        super("답변을 생성할 수 없습니다");
    }
}
