package com.usktea.plainold.exceptions;

public class CreateInquiryFailed extends RuntimeException {
    public CreateInquiryFailed (Exception exception) {
        super(exception.getMessage());
    }
}
