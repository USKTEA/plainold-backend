package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.TokenDto;
import com.usktea.plainold.exceptions.LoginFailed;
import com.usktea.plainold.models.user.Password;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.models.user.Username;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class LoginService {
    private final FindUserService findUserService;
    private final IssueTokenService issueTokenService;
    private final PasswordEncoder passwordEncoder;

    public LoginService(FindUserService findUserService,
                        IssueTokenService issueTokenService,
                        PasswordEncoder passwordEncoder) {
        this.findUserService = findUserService;
        this.issueTokenService = issueTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenDto login(Username username, Password password) {
        try {
            Users user = findUserService.find(username);

            user.authenticate(password, passwordEncoder);

            TokenDto tokenDto = issueTokenService.issue(username);

            return tokenDto;
        } catch (Exception exception) {
            throw new LoginFailed();
        }
    }
}