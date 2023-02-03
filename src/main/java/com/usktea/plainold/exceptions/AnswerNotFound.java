package com.usktea.plainold.exceptions;

public class AnswerNotFound extends RuntimeException {
    public AnswerNotFound() {
        super("답변을 찾을 수 없습니다");
    }
}
