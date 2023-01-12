package com.usktea.plainold.models;

import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.utils.JwtUtil;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class RefreshToken {
    @Id
    @Embedded
    private Username username;
    private String number;

    public RefreshToken() {
    }

    public RefreshToken(Username username, String number) {
        this.username = username;
        this.number = number;
    }

    public static RefreshToken of(Username username, String number) {
        return new RefreshToken(username, number);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        RefreshToken otherRefreshToken = (RefreshToken) object;

        return Objects.equals(username, otherRefreshToken.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public void update(String newNumber) {
        this.number = newNumber;
    }

    public Username username() {
        return username;
    }

    public String getNextAccessToken(JwtUtil jwtUtil) {
        return jwtUtil.encode(username.value());
    }

    public String getNextRefreshToken(JwtUtil jwtUtil) {
        return jwtUtil.encode(UUID.randomUUID());
    }

    public String getNextVersion(JwtUtil jwtUtil) {
        String tokenNumber = jwtUtil.encode(UUID.randomUUID());

        this.number = tokenNumber;

        return number;
    }
}
