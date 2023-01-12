package com.usktea.plainold.models.user;

import com.usktea.plainold.exceptions.InvalidUsername;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class Username implements Serializable {
    @Column(name = "username")
    private String value;

    public Username() {
    }

    public Username(String value) {
        setValue(value);
    }

    private void setValue(String value) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

        Matcher matcher = pattern.matcher(value);

        if (!matcher.find()) {
            throw new InvalidUsername();
        }

        this.value = value;
    }

    public static Username of(String userName) {
        return new Username(userName);
    }

    public String beforeAt() {
        return value.split("@")[0];
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Username otherUsername = (Username) object;

        return Objects.equals(value, otherUsername.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String value() {
        return value;
    }
}
