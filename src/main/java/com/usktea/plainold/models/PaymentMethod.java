package com.usktea.plainold.models;

public enum PaymentMethod {
    CASH("CASH"),
    KAKAOPAY("KAKAOPAY");

    private String method;

    PaymentMethod() {
    }

    PaymentMethod(String method) {
        this.method = method;
    }
}
