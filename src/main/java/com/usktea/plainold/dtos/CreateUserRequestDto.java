package com.usktea.plainold.dtos;

import javax.validation.constraints.NotBlank;

public class CreateUserRequestDto {
    @NotBlank
    private String nickname;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public CreateUserRequestDto() {
    }

    public String getNickname() {
        return nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
