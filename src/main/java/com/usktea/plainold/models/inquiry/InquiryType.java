package com.usktea.plainold.models.inquiry;

public enum InquiryType {
    PUBLIC("전체공개"),
    SECRET("비밀글");

    private String type;

    InquiryType() {
    }

    InquiryType(String type) {
        this.type = type;
    }
}
