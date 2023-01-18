package com.usktea.plainold.models.review;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ReviewImageUrl {
    @Column(name = "reviewImageUrl")
    private String value;

    public ReviewImageUrl() {
    }

    public ReviewImageUrl(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        ReviewImageUrl otherReviewImageUrl = (ReviewImageUrl) other;

        return Objects.equals(value, otherReviewImageUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
