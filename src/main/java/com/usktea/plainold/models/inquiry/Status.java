package com.usktea.plainold.models.inquiry;

public enum Status {
    PENDING("처리대기"),
    FINISHED("답변완료"),
    DELETED("삭제처리");

    private String status;

    Status() {
    }

    Status(String status) {
        this.status = status;
    }
}
