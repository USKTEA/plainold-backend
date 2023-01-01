package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.IncorrectQuantity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Quantity {
    @Column(name = "quantity")
    private Long amount;

    public Quantity() {
    }

    public Quantity(Long amount) {
        setAmount(amount);
    }

    private void setAmount(Long amount) {
        if (Objects.equals(amount, null) || amount <= 0L) {
            throw new IncorrectQuantity();
        }

        this.amount = amount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Quantity otherQuantity = (Quantity) object;

        return Objects.equals(amount, otherQuantity.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    public Long amount() {
        return amount;
    }
}
