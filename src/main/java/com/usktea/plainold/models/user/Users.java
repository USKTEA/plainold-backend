package com.usktea.plainold.models.user;

import com.usktea.plainold.exceptions.LoginFailed;
import com.usktea.plainold.models.common.Name;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Users {
    @Id
    @Embedded
    private Username username;

    @Embedded
    private Password password;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "nickname"))
    private Name nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    public Users() {
    }

    public Users(Username username, Name nickname, Role role, UserStatus userStatus) {
        this.username = username;
        this.nickname = nickname;
        this.role = role;
        this.userStatus = userStatus;
        this.registeredAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Users fake(Username username) {
        return new Users(username, new Name("김뚜루"), Role.MEMBER, UserStatus.ACTIVE);
    }

    public void changePassword(Password password, PasswordEncoder passwordEncoder) {
        this.password = Password.of(passwordEncoder.encode(password.getValue()));
    }

    public Username username() {
        return username;
    }

    public void authenticate(Password password, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(password.getValue(), this.password.getValue())) {
            throw new LoginFailed();
        }
    }
}
