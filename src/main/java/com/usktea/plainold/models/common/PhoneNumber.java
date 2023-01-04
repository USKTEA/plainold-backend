package com.usktea.plainold.models.common;

import com.usktea.plainold.exceptions.IncorrectPhoneNumber;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class PhoneNumber {
    private String value;

    public PhoneNumber() {
    }

    public PhoneNumber(String value) {
        setValue(value);
    }

    private void setValue(String value) {
        Pattern pattern = Pattern.compile("^[0-9]{3}-[0-9]{3,4}-[0-9]{4}");

        Matcher matcher = pattern.matcher(value);

        if (!matcher.find()) {
            throw new IncorrectPhoneNumber();
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

        PhoneNumber otherPhoneNumber = (PhoneNumber) other;

        return Objects.equals(value, otherPhoneNumber.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String value() {
        return value;
    }
}
