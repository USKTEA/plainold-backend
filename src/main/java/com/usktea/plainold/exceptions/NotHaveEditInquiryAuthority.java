package com.usktea.plainold.exceptions;

public class NotHaveEditInquiryAuthority extends RuntimeException {
    public NotHaveEditInquiryAuthority() {
        super("해당 권한이 없습니다");
    }
}
