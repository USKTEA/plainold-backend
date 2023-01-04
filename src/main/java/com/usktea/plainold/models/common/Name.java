package com.usktea.plainold.models.common;

import com.usktea.plainold.exceptions.IncorrectName;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class Name {
    private String value;

    public Name() {
    }

    public Name(String value) {
        setValue(value);
    }

    private void setValue(String value) {
        Pattern pattern = Pattern.compile("^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,}$");

        Matcher matcher = pattern.matcher(value);

        if (!matcher.find()) {
            throw new IncorrectName();
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

        Name otherName = (Name) other;

        return Objects.equals(value, otherName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String value() {
        return value;
    }
}
