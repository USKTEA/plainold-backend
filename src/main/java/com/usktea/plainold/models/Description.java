package com.usktea.plainold.models;

import com.usktea.plainold.dtos.DescriptionDto;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Description {
    private Summary summary;
    private Detail detail;

    public Description() {
    }

    public Description(Summary summary, Detail detail) {
        this.summary = summary;
        this.detail = detail;
    }

    public static Description fake(String summary, String detail) {
        return new Description(new Summary(summary), new Detail(detail));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Description otherDescription = (Description) other;

        return Objects.equals(summary, otherDescription.summary)
                && Objects.equals(detail, otherDescription.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summary, detail);
    }

    public DescriptionDto toDto() {
        return new DescriptionDto(summary.content(), detail.content());
    }
}
