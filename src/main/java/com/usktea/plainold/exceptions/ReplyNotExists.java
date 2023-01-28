package com.usktea.plainold.exceptions;

public class ReplyNotExists extends RuntimeException {
    public ReplyNotExists() {
        super("댓글이 존재하지 않습니다");
    }
}
