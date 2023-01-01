package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.IncorrectEmail;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class Email {
    @Column(name = "email")
    private String value;

    public Email() {
    }

    public Email(String value) {
        setValue(value);
    }

    private void setValue(String value) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

        Matcher matcher = pattern.matcher(value);

        if (!matcher.find()) {
            throw new IncorrectEmail();
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

        Email otherEmail = (Email) other;

        return Objects.equals(value, otherEmail.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

