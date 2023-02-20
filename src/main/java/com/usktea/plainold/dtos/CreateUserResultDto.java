package com.usktea.plainold.dtos;

public class CreateUserResultDto {
    private String username;

    public CreateUserResultDto() {
    }

    public CreateUserResultDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
