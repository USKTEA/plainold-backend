package com.usktea.plainold.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Detail {
    @Column(name = "productDetail")
    private String content;

    public Detail() {
    }

    public Detail(String content) {
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

        Detail otherDetail = (Detail) other;

        return Objects.equals(content, otherDetail.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    public String content() {
        return content;
    }
}
