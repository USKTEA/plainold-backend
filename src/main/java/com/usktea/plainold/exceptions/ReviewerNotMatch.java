package com.usktea.plainold.exceptions;

public class ReviewerNotMatch extends RuntimeException {
    public ReviewerNotMatch() {
        super("권한이 없습니다");
    }
}
