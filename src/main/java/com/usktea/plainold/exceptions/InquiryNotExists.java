package com.usktea.plainold.exceptions;

public class InquiryNotExists extends RuntimeException {
    public InquiryNotExists() {
        super("상품문의가 존재하지 않습니다");
    }
}
