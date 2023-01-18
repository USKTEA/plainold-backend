package com.usktea.plainold.exceptions;

public class CreateReviewFailed extends RuntimeException {
    public CreateReviewFailed(Exception exception) {
        super(exception.getMessage());
    }
}
