package com.usktea.plainold.dtos;

public class EditUserResultDto {
    private String nickname;

    public EditUserResultDto() {
    }

    public EditUserResultDto(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
