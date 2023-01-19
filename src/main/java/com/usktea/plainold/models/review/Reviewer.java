package com.usktea.plainold.models.review;

import com.usktea.plainold.dtos.ReviewerDto;
import com.usktea.plainold.models.user.Username;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Reviewer {
    private Username username;
    private Nickname nickname;

    public Reviewer() {
    }

    public Reviewer(Username username, Nickname nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public static Reviewer fake(String name) {
        Username username = new Username("tjrxo1234@gmail.com");

        return new Reviewer(username, new Nickname(name));
    }

    public static Reviewer fake(Username username) {
        return new Reviewer(username, new Nickname("닉네임입니다"));
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

        Reviewer otherReviewer = (Reviewer) object;

        return Objects.equals(username, otherReviewer.username)
                && Objects.equals(nickname, otherReviewer.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, nickname);
    }

    public ReviewerDto toDto() {
        return new ReviewerDto(username.value(), nickname.value());
    }
}
