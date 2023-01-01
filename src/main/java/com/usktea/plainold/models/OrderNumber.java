package com.usktea.plainold.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderNumber implements Serializable {
    @Column(name = "orderNumber")
    private String value;

    public OrderNumber() {
    }

    public OrderNumber(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        OrderNumber otherOrderNumber = (OrderNumber) other;

        return Objects.equals(value, otherOrderNumber.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String value() {
        return value;
    }
}
