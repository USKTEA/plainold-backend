package com.usktea.plainold.dtos;

public class ReplierDto {
    private String username;
    private String nickname;

    public ReplierDto() {
    }

    public ReplierDto(String username, String nickname) {
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
