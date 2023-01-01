package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.IncorrectAddress2;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address2 {
    @Column(name = "address2")
    private String value;

    public Address2() {
    }

    public Address2(String value) {
        setValue(value);
    }

    private void setValue(String value) {
        if (value.isBlank()) {
            throw new IncorrectAddress2();
        }

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

        Address2 otherAddress2 = (Address2) other;

        return Objects.equals(value, otherAddress2.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String value() {
        return value;
    }
}
