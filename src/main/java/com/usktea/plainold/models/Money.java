package com.usktea.plainold.models;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Money {
    private Long amount;

    public Money() {
    }

    public Money(Long amount) {
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

        Money otherMoney = (Money) other;

        return Objects.equals(amount, otherMoney.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    public Long amount() {
        return amount;
    }
}
