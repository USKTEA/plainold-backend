package com.usktea.plainold.exceptions;

public class InvalidRgbValue extends RuntimeException {
    public InvalidRgbValue() {
        super("RGB값은 0-255 사이의 숫자만 허용됩니다");
    }
}
