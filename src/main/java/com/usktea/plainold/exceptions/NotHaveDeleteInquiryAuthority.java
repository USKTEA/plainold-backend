package com.usktea.plainold.exceptions;

public class NotHaveDeleteInquiryAuthority extends RuntimeException {
    public NotHaveDeleteInquiryAuthority() {
        super("삭제 권한이 없습니다");
    }
}
