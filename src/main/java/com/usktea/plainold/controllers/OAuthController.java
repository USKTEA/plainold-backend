package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.oAuth.OAuthService;
import com.usktea.plainold.applications.oAuth.OAuthServiceFactory;
import com.usktea.plainold.dtos.GetRedirectUrlResultDto;
import com.usktea.plainold.dtos.LoginResultDto;
import com.usktea.plainold.dtos.OAuthLoginRequestDto;
import com.usktea.plainold.dtos.TokenDto;
import com.usktea.plainold.exceptions.InvalidProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("oauth")
public class OAuthController {
    private final OAuthServiceFactory oAuthServiceFactory;

    public OAuthController(OAuthServiceFactory oAuthServiceFactory) {
        this.oAuthServiceFactory = oAuthServiceFactory;
    }

    @GetMapping("{provider}")
    public GetRedirectUrlResultDto redirectUrl(@PathVariable String provider) {
        OAuthService oAuthService = oAuthServiceFactory.getOAuthService(provider);

        if (Objects.isNull(oAuthService)) {
            throw new InvalidProvider();
        }

        String redirectUrl = oAuthService.getAuthorizationUrl();

        return new GetRedirectUrlResultDto(redirectUrl);
    }

    @PostMapping("session")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResultDto login(
            HttpServletResponse response,
            @RequestBody OAuthLoginRequestDto oAuthLoginRequestDto
    ) {
        String provider = oAuthLoginRequestDto.getProvider();
        String code = oAuthLoginRequestDto.getCode();

        OAuthService oAuthService = oAuthServiceFactory.getOAuthService(provider);

        if (Objects.isNull(oAuthService)) {
            throw new InvalidProvider();
        }

        TokenDto token = oAuthService.login(code);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .domain("localhost")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return new LoginResultDto(token.getAccessToken());
    }

    @ExceptionHandler(InvalidProvider.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidProvider(Exception exception) {
        return exception.getMessage();
    }
}
