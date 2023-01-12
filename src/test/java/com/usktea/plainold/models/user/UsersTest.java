package com.usktea.plainold.models.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ActiveProfiles("test")
class UsersTest {
    @Test
    void authenticate() {
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        Password password = new Password("Password1234!");

        Users user = new Users();

        user.changePassword(password, passwordEncoder);

        assertDoesNotThrow(() -> user.authenticate(password, passwordEncoder));
    }
}
