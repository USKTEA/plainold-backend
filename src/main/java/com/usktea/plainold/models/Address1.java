package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.IncorrectAddress1;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class Address1 {
    @Column(name = "address1")
    private String value;

    public Address1() {
    }

    public Address1(String value) {
        setValue(value);
    }

    private void setValue(String value) {
        Pattern pattern =
                Pattern.compile("(([가-힣A-Za-z·\\d~\\-.]{2,}(로|길).[\\d]+)|([가-힣A-Za-z·\\d~\\-.]+(읍|동)\\s)[\\d]+)");

        Matcher matcher = pattern.matcher(value);

        if (!matcher.find()) {
            throw new IncorrectAddress1();
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

        Address1 otherAddress1 = (Address1) other;

        return Objects.equals(value, otherAddress1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String value() {
        return value;
    }
}
