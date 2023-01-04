package com.usktea.plainold.models.product;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductId implements Serializable {
    @Column(name = "productId")
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

    public void setProductId(Long value) {
        this.value = value;
    }

    public Long getProductId() {
        return value;
    }
}
