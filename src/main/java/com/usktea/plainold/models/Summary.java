package com.usktea.plainold.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Summary {
    @Column(name = "productSummary")
    private String content;

    public Summary() {
    }

    public Summary(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Summary otherSummary = (Summary) other;

        return Objects.equals(content, otherSummary.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    public String content() {
        return content;
    }
}
