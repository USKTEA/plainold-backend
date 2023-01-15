package com.usktea.plainold.models.common;

import com.usktea.plainold.exceptions.IncorrectQuantity;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Quantity {
    private Long amount;

    public Quantity() {
    }

    public Quantity(Long amount) {
        setAmount(amount);
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

    public Long getAmount() {
        return amount;
    }

    private void setAmount(Long amount) {
        if (Objects.equals(amount, null) || amount <= 0L) {
            throw new IncorrectQuantity();
        }

        this.amount = amount;
    }

    public Quantity add(Quantity quantity) {
        return new Quantity(this.amount + quantity.getAmount());
    }
}
