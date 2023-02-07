package com.usktea.plainold.exceptions;

public class CancelRequestNotFound extends RuntimeException {
    public CancelRequestNotFound() {
        super("취소요청이 존재하지 않습니다");
    }
}
