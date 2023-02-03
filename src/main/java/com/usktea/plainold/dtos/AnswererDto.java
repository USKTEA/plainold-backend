package com.usktea.plainold.dtos;

public class AnswererDto {
    private String username;
    private String nickname;

    public AnswererDto() {
    }

    public AnswererDto(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }
}
