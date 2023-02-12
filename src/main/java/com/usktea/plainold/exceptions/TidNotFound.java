package com.usktea.plainold.exceptions;

public class TidNotFound extends RuntimeException {
    public TidNotFound() {
        super("결제요청을 찾을 수 없습니다");
    }
}
