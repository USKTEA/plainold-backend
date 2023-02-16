package com.usktea.plainold.dtos;

import com.usktea.plainold.models.user.Nickname;
import com.usktea.plainold.models.user.Username;

public class EditUserRequest {
    private Username username;
    private Nickname nickname;

    public EditUserRequest(Username username, Nickname nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public static EditUserRequest of(EditUserRequestDto editUserRequestDto) {
        return new EditUserRequest(
                new Username(editUserRequestDto.getUsername()),
                new Nickname(editUserRequestDto.getNickname())
        );
    }

    public static EditUserRequest fake(Username username) {
        return new EditUserRequest(
                username,
                new Nickname("김뚜루")
        );
    }

    public Username username() {
        return username;
    }

    public Nickname nickname() {
        return nickname;
    }
}
