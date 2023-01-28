package com.usktea.plainold.exceptions;

public class ReplierNotMatch extends RuntimeException {
    public ReplierNotMatch() {
        super("수정 권한이 없습니다");
    }
}
