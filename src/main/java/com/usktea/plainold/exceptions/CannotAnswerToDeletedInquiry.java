package com.usktea.plainold.exceptions;

public class CannotAnswerToDeletedInquiry extends RuntimeException {
    public CannotAnswerToDeletedInquiry() {
        super("삭제된 문의에는 답변할 수 없습니다");
    }
}
