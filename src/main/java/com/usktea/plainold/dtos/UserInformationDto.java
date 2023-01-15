package com.usktea.plainold.dtos;

import com.usktea.plainold.models.user.Username;

public class UserInformationDto {
    public String username;

    public UserInformationDto() {
    }

    public UserInformationDto(Username username) {
        this.username = username.value();
    }

    public String getUsername() {
        return username;
    }
}
