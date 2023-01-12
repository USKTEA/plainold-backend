package com.usktea.plainold.models.option;

public enum Size {
    M("M"),
    L("L"),
    XL("XL"),
    FREE("");

    private String value;

    Size(String value) {
        this.value = value;
    }
}
