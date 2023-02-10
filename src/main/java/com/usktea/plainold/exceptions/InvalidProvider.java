package com.usktea.plainold.exceptions;

public class InvalidProvider extends RuntimeException {
    public InvalidProvider() {
        super("무효한 제공자입니다");
    }
}
