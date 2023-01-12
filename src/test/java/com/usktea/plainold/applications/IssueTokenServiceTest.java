package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.TokenDto;
import com.usktea.plainold.exceptions.RefreshTokenNotFound;
import com.usktea.plainold.exceptions.ReissueTokenFailed;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.RefreshToken;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.repositories.RefreshTokenRepository;
import com.usktea.plainold.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class IssueTokenServiceTest {
    private RefreshTokenRepository refreshTokenRepository;
    private IssueTokenService issueTokenService;
    private JwtUtil jwtUtil;

    @BeforeEach
    void setup() {
        jwtUtil = new JwtUtil("SECRET");
        refreshTokenRepository = mock(RefreshTokenRepository.class);
        issueTokenService = new IssueTokenService(refreshTokenRepository, jwtUtil);
    }

    @Test
    void issue() {
        Username username = new Username("tjrxo1234@gmail.com");

        TokenDto tokenDto = issueTokenService.issue(username);

        assertThat(tokenDto.getAccessToken()).contains(".");
        assertThat(tokenDto.getRefreshToken()).contains(".");

        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    void whenReissueSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");

        String token = jwtUtil.encode(UUID.randomUUID());

        given(refreshTokenRepository.findByNumber(token))
                .willReturn(Optional.of(RefreshToken.of(username, token)));

        TokenDto newToken = issueTokenService.reissue(token);

        assertThat(newToken).isNotNull();
    }

    @Test
    void reissueFail() {
        String token = jwtUtil.encode(UUID.randomUUID());

        given(refreshTokenRepository.findByNumber(token))
                .willReturn(Optional.empty());

        assertThrows(RefreshTokenNotFound.class,
                () -> issueTokenService.reissue(token));
    }
}
