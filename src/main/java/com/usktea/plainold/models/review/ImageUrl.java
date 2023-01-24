package com.usktea.plainold.models.review;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ImageUrl {
    @Column(name = "imageUrl")
    private String value;

    public ImageUrl() {
    }

    public ImageUrl(String value) {
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

        ImageUrl otherImageUrl = (ImageUrl) other;

        return Objects.equals(value, otherImageUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public ImageUrl change(ImageUrl imageUrl) {
        return new ImageUrl(imageUrl.value);
    }
}
