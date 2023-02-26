package com.usktea.plainold.exceptions;

public class LikeNotExists extends RuntimeException {
    public LikeNotExists() {
        super("좋아요가 존재하지 않습니다");
    }
}
