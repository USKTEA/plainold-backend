package com.usktea.plainold.exceptions;

public class OrderNotCreated extends RuntimeException {
    public OrderNotCreated(String message) {
        super(message);
    }
}
