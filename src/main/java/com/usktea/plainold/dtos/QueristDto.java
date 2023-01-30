package com.usktea.plainold.dtos;

public class QueristDto {
    private String username;
    private String nickname;

    public QueristDto() {
    }

    public QueristDto(String username, String nickname) {
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
