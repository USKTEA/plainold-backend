package com.usktea.plainold.exceptions;

public class ReviewNotFound extends RuntimeException {
    public ReviewNotFound() {
        super("구매평이 존재하지 않습니다");
    }
}
