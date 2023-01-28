package com.usktea.plainold.models.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Comment {
    @Column(name = "comment")
    private String value;

    public Comment() {
    }

    public Comment(String value) {
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

        Comment otherComment = (Comment) other;

        return Objects.equals(value, otherComment.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public Comment change(Comment comment) {
        return new Comment(comment.getValue());
    }
}
