package com.usktea.plainold.models.review;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Nickname {
    @Column(name = "nickname")
    private String value;

    public Nickname() {
    }

    public Nickname(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
