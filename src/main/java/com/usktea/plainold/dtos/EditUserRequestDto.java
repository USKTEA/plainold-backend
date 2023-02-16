package com.usktea.plainold.dtos;

import javax.validation.constraints.NotBlank;

public class EditUserRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String nickname;

    public EditUserRequestDto() {
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }
}
