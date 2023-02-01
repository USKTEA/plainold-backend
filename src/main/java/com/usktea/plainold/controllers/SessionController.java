package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.login.LoginService;
import com.usktea.plainold.dtos.LoginRequestDto;
import com.usktea.plainold.dtos.LoginResultDto;
import com.usktea.plainold.dtos.TokenDto;
import com.usktea.plainold.exceptions.LoginFailed;
import com.usktea.plainold.models.user.Password;
import com.usktea.plainold.models.user.Username;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("session")
public class SessionController {
    private final LoginService loginService;

    public SessionController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResultDto login(HttpServletResponse response,
                                @Valid @RequestBody LoginRequestDto loginRequestDto
    ) {
        try {
            Username username = Username.of(loginRequestDto.getUsername());
            Password password = Password.of(loginRequestDto.getPassword());

            TokenDto token = loginService.login(username, password);

            ResponseCookie cookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                    .httpOnly(true)
                    .path("/")
                    .sameSite("Lax")
                    .domain("localhost")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return new LoginResultDto(token.getAccessToken());
        } catch (Exception exception) {
            throw new LoginFailed();
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidRequest() {
        return "이메일 혹은 비밀번호가 잘못 되었습니다";
    }

    @ExceptionHandler(LoginFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailed(Exception exception) {
        return exception.getMessage();
    }
}
