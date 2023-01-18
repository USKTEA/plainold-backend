package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.TokenDto;
import com.usktea.plainold.exceptions.LoginFailed;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.user.Password;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class LoginServiceTest {
    private LoginService loginService;
    private GetUserService getUserService;
    private IssueTokenService issueTokenService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        issueTokenService = mock(IssueTokenService.class);
        passwordEncoder = new Argon2PasswordEncoder();
        loginService = new LoginService(getUserService, issueTokenService, passwordEncoder);
    }

    @Test
    void loginSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        Password password = new Password("Password1234!");

        Users user = Users.fake(username);

        user.changePassword(password, passwordEncoder);

        given(issueTokenService.issue(username))
                .willReturn(TokenDto.fake());

        given(getUserService.find(username))
                .willReturn(user);

        TokenDto token = loginService.login(username, password);

        assertThat(token).isNotNull();
    }

    @Test
    void loginWithWrongUsername() {
        Username username = new Username("notTjrxo1234@gmail.com");
        Password password = new Password("Password1234!");

        given(getUserService.find(username))
                .willThrow(UserNotExists.class);

        assertThrows(LoginFailed.class, () -> loginService.login(username, password));
    }

    @Test
    void loginWithWrongPassword() {
        Username username = new Username("tjrxo1234@gmail.com");
        Password password = new Password("Password1234!");
        Password wrongPassword = new Password("notPassword1234!");

        Users user = Users.fake(username);

        user.changePassword(password, passwordEncoder);

        given(getUserService.find(username)).willReturn(user);

        assertThrows(LoginFailed.class,
                () -> loginService.login(username, wrongPassword));
    }
}
