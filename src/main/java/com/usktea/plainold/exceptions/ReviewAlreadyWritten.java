package com.usktea.plainold.exceptions;

public class ReviewAlreadyWritten extends RuntimeException {
    public ReviewAlreadyWritten() {
        super("이미 작성한 구매평이 있습니다");
    }
}
