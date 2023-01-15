package com.usktea.plainold.models.option;

import java.util.Objects;

public enum Size {
    M("M"),
    L("L"),
    XL("XL"),
    FREE("");

    private String value;

    Size(String value) {
        this.value = value;
    }

    public boolean isFree() {
        return Objects.equals(value, "");
    }
}
