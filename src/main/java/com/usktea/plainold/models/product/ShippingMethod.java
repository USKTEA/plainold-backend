package com.usktea.plainold.models.product;

public enum ShippingMethod {
    Parcel("택배"),
    Post("우채국");

    private String method;

    ShippingMethod() {
    }

    ShippingMethod(String method) {
        this.method = method;
    }

    public String value() {
        return method;
    }
}
