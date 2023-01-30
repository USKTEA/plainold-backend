package com.usktea.plainold.models.inquiry;

import com.usktea.plainold.dtos.QueristDto;
import com.usktea.plainold.models.review.Nickname;
import com.usktea.plainold.models.user.Username;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Querist {
    private Username username;
    private Nickname nickname;

    public Querist() {
    }

    public Querist(Username username, Nickname nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public static Querist fake(Username username) {
        return new Querist(
                username,
                new Nickname("김뚜루")
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Querist otherQuerist = (Querist) object;

        return Objects.equals(username, otherQuerist.username)
                && Objects.equals(nickname, otherQuerist.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, nickname);
    }

    public QueristDto toDto() {
        return new QueristDto(
                username.value(),
                nickname.value()
        );
    }

    public boolean isSameUser(Username username) {
        return Objects.equals(this.username, username);
    }
}
