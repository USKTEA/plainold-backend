package com.usktea.plainold.exceptions;

public class AnswerCannotBeEdited extends RuntimeException {
    public AnswerCannotBeEdited() {
        super("답변을 수정할 수 없습니다");
    }
}
