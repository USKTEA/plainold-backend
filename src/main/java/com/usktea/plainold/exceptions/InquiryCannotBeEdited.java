package com.usktea.plainold.exceptions;

public class InquiryCannotBeEdited extends RuntimeException {
    public InquiryCannotBeEdited() {
        super("답변처리가 완료되거나 삭제된 상품문의는 수정할 수 없습니다");
    }
}
