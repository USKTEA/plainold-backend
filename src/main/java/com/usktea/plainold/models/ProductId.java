package com.usktea.plainold.models;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductId {
    private Long value;

    public ProductId() {
    }

    public ProductId(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        ProductId otherProductId = (ProductId) object;

        return Objects.equals(value, otherProductId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public Long value() {
        return value;
    }
}
