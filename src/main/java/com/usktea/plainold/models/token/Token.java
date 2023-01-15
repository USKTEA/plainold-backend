package com.usktea.plainold.models.token;

import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.utils.JwtUtil;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Token {
    @Id
    @Embedded
    private Username username;
    private String number;

    public Token() {
    }

    public Token(Username username, String number) {
        this.username = username;
        this.number = number;
    }

    public static Token of(Username username, String number) {
        return new Token(username, number);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Token otherToken = (Token) object;

        return Objects.equals(username, otherToken.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public Username username() {
        return username;
    }

    public String getNextAccessToken(JwtUtil jwtUtil) {
        return jwtUtil.encode(username.value());
    }

    public String getNextVersion(JwtUtil jwtUtil) {
        String tokenNumber = jwtUtil.encode(UUID.randomUUID());

        this.number = tokenNumber;

        return number;
    }
}
