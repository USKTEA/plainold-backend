package com.usktea.plainold.models.answer;

import com.usktea.plainold.dtos.AnswererDto;
import com.usktea.plainold.models.user.Nickname;
import com.usktea.plainold.models.user.Username;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Answerer {
    private Username username;
    private Nickname nickname;

    public Answerer() {
    }

    public Answerer(Username username, Nickname nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public static Answerer fake(Username username) {
        return new Answerer(
                username,
                new Nickname("닉네임")
        );
    }

    public Username getUsername() {
        return username;
    }

    public Nickname getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Answerer otherAnswerer = (Answerer) object;

        return Objects.equals(username, otherAnswerer.username)
                && Objects.equals(nickname, otherAnswerer.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, nickname);
    }

    public AnswererDto toDto() {
        return new AnswererDto(
                username.value(),
                nickname.value()
        );
    }
}
