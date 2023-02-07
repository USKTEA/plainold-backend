package com.usktea.plainold.models.cancelRequest;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Content {
    @Column(name = "content")
    private String value;

    public Content() {
    }

    public Content(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
