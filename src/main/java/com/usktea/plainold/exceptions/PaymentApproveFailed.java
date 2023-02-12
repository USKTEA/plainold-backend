package com.usktea.plainold.exceptions;

public class PaymentApproveFailed extends RuntimeException {
    public PaymentApproveFailed(String message) {
        super(message);
    }
}
