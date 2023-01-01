package com.usktea.plainold.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserName {
    @Column(name = "userName")
    private String value;

    public UserName() {
    }

    public UserName(String value) {
        this.value = value;
    }

    public String beforeAt() {
        return value.split("@")[0];
    }
}
