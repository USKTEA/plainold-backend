package com.usktea.plainold.dtos;

import javax.validation.constraints.NotBlank;

public class LoginRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public LoginRequestDto() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
