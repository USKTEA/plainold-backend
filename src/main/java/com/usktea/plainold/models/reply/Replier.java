package com.usktea.plainold.models.reply;

import com.usktea.plainold.dtos.ReplierDto;
import com.usktea.plainold.models.review.Nickname;
import com.usktea.plainold.models.user.Username;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Replier {
    private Username username;
    private Nickname nickname;

    public Replier() {
    }

    public Replier(Username username, Nickname nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public static Replier fake(String username) {
        return new Replier(new Username(username), new Nickname("김뚜루"));
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

        Replier otherReplier = (Replier) object;

        return Objects.equals(username, otherReplier.username)
                && Objects.equals(nickname, otherReplier.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, nickname);
    }

    public ReplierDto toDto() {
        return new ReplierDto(username.value(), nickname.value());
    }
}
