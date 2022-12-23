package com.usktea.plainold.models;

public enum ProductStatus {
    ON_SALE("ON_SALE"),
    SOLD_OUT("SOLD_OUT");

    private String status;

    ProductStatus() {
    }

    ProductStatus(String status) {
        this.status = status;
    }

    public String value() {
        return status;
    }
}
