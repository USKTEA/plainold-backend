package com.usktea.plainold.dtos;

public class ReviewerDto {
    private String username;
    private String nickname;

    public ReviewerDto() {
    }

    public ReviewerDto(String username, String nickname) {
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
