package com.usktea.plainold.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductImageUrl {
    @Column(name = "productImageUrl")
    private String value;

    public ProductImageUrl() {
    }

    public ProductImageUrl(String value) {
        this.value = value;
    }

    public String productImageUrl() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        ProductImageUrl otherProductImageUrl = (ProductImageUrl) other;

        return Objects.equals(value, otherProductImageUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
