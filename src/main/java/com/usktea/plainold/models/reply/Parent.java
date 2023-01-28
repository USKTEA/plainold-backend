package com.usktea.plainold.models.reply;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Parent {
    @Column(name = "parent")
    private Long value;

    public Parent() {
    }

    public Parent(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Parent otherParent = (Parent) object;

        return Objects.equals(value, otherParent.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
