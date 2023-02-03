package com.usktea.plainold.models.user;

import com.usktea.plainold.exceptions.LoginFailed;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Users {
    @Id
    @Embedded
    private Username username;

    @Embedded
    private Password password;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "nickname"))
    private Nickname nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    public Users() {
    }

    public Users(Username username, Nickname nickname, Role role, UserStatus userStatus) {
        this.username = username;
        this.nickname = nickname;
        this.role = role;
        this.userStatus = userStatus;
        this.registeredAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Users fake(Username username) {
        return new Users(
                username,
                new Nickname("김뚜루"),
                Role.MEMBER,
                UserStatus.ACTIVE);
    }

    public static Users fake(Username username, Role role) {
        return new Users(
                username,
                new Nickname("손님"),
                role,
                UserStatus.ACTIVE);
    }

    public static Users fake(Role role) {
        return new Users(
                new Username("admin@admin.com"),
                new Nickname("관리자"),
                role,
                UserStatus.ACTIVE);
    }

    public void changePassword(Password password, PasswordEncoder passwordEncoder) {
        this.password = Password.of(passwordEncoder.encode(password.getValue()));
    }

    public void authenticate(Password password, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(password.getValue(), this.password.getValue())) {
            throw new LoginFailed();
        }
    }

    public Username username() {
        return username;
    }

    public Nickname nickname() {
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

        Users otherUsers = (Users) object;

        return Objects.equals(username, otherUsers.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public Role role() {
        return role;
    }

    public boolean isGuest() {
        return Objects.equals(this.role, Role.GUEST);
    }

    public boolean isAdmin() {
        return Objects.equals(this.role, Role.ADMIN);
    }
}
