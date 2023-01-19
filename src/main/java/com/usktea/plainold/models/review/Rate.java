package com.usktea.plainold.models.review;

import com.usktea.plainold.exceptions.InvalidRate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Rate {
    @Column(name = "rate")
    private Integer value;

    public Rate() {
    }

    public Rate(Integer value) {
        setValue(value);
    }

    private void setValue(Integer value) {
        if (value > 5 || value < 1) {
            throw new InvalidRate();
        }

        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Rate otherRate = (Rate) object;

        return Objects.equals(value, otherRate.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public Rate change(Rate rate) {
        return new Rate(rate.getValue());
    }
}
