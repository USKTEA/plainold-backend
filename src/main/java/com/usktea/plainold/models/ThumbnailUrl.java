package com.usktea.plainold.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ThumbnailUrl {
    @Column(name = "thumbnailUrl")
    private String value;

    public ThumbnailUrl() {
    }

    public ThumbnailUrl(String value) {
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

        ThumbnailUrl otherThumbnailUrl = (ThumbnailUrl) other;

        return Objects.equals(value, otherThumbnailUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String value() {
        return value;
    }
}
