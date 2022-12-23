package com.usktea.plainold.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public enum ShippingMethod {
    Parcel("택배"),
    Post("우채국");

    @Column(name = "shippingMethod")
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
