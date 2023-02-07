package com.usktea.plainold.exceptions;

public class CancelOrderFailed extends RuntimeException {
    public CancelOrderFailed(String message) {
        super(message);
    }
}
