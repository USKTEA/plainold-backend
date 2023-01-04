package com.usktea.plainold.models.order;

import com.usktea.plainold.exceptions.IncorrectZipCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class ZipCode {
    @Column(name = "zipCode")
    private String value;

    public ZipCode() {
    }

    public ZipCode(String value) {
        setValue(value);
    }

    private void setValue(String value) {
        Pattern pattern = Pattern.compile("^\\d{3}-?\\d{3}$");

        Matcher matcher = pattern.matcher(value);

        if (!matcher.find()) {
            throw new IncorrectZipCode();
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

        ZipCode otherZipCode = (ZipCode) other;

        return Objects.equals(value, otherZipCode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String value() {
        return value;
    }
}
