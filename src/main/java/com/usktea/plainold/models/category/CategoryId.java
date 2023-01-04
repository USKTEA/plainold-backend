package com.usktea.plainold.models.category;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CategoryId {
    @Column(name = "categoryId")
    private Long value;

    public CategoryId() {
    }

    public CategoryId(Long value) {
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

        CategoryId otherCategoryId = (CategoryId) other;

        return Objects.equals(value, otherCategoryId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public Long value() {
        return value;
    }

    public boolean isNull() {
        return Objects.equals(value, null);
    }
}
