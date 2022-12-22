package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.InvalidPrice;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Price {
    @Column(name = "price")
    private Long amount;

    public Price() {
    }

    public Price(Long amount) {
        if (amount < 0) {
            throw new InvalidPrice();
        }

        this.amount = amount;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Price otherPrice = (Price) other;

        return Objects.equals(amount, otherPrice.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    public Long amount() {
        return amount;
    }
}
