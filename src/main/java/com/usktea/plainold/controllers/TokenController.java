package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.token.IssueTokenService;
import com.usktea.plainold.dtos.ReissuedTokenDto;
import com.usktea.plainold.dtos.TokenDto;
import com.usktea.plainold.exceptions.ReissueTokenFailed;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("token")
public class TokenController {
    private final IssueTokenService issueTokenService;

    public TokenController(IssueTokenService issueTokenService) {
        this.issueTokenService = issueTokenService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReissuedTokenDto reissueToken(
            HttpServletResponse response,
            @CookieValue(value = "refreshToken") String refreshToken
    ) {
        try {
            TokenDto token = issueTokenService.reissue(refreshToken);

            ResponseCookie cookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                    .httpOnly(true)
                    .path("/")
                    .sameSite("Lax")
                    .domain("localhost")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return new ReissuedTokenDto(token.getAccessToken());
        } catch (Exception exception) {
            throw new ReissueTokenFailed();
        }
    }

    @ExceptionHandler(ReissueTokenFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidToken(Exception exception, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setMaxAge(0);;

        response.addCookie(cookie);

        return exception.getMessage();
    }
}
