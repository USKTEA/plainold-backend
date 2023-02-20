package com.usktea.plainold.dtos;

import com.usktea.plainold.models.user.Nickname;
import com.usktea.plainold.models.user.Password;
import com.usktea.plainold.models.user.Username;

public class CreateUserRequest {
    private Nickname nickname;
    private Username username;
    private Password password;

    public CreateUserRequest(Nickname nickname, Username username, Password password) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
    }

    public static CreateUserRequest of(CreateUserRequestDto createUserRequestDto) {
        return new CreateUserRequest(
                new Nickname(createUserRequestDto.getNickname()),
                new Username(createUserRequestDto.getUsername()),
                new Password(createUserRequestDto.getPassword())
        );
    }

    public static CreateUserRequest fake(Username username) {
        return new CreateUserRequest(
                new Nickname("김뚜루"),
                username,
                new Password("Password1234!")
        );
    }

    public Username username() {
        return username;
    }

    public Nickname nickname() {
        return nickname;
    }

    public Password password() {
        return password;
    }
}
