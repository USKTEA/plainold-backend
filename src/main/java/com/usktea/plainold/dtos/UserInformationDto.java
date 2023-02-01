package com.usktea.plainold.dtos;

import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;

public class UserInformationDto {
    public String username;
    public String role;

    public UserInformationDto() {
    }

    public UserInformationDto(Username username, Role role) {
        this.username = username.value();
        this.role = role.name();
    }

    public String getUsername() {
        return username;
    }
}
