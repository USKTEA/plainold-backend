package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.TokenDto;
import com.usktea.plainold.exceptions.RefreshTokenNotFound;
import com.usktea.plainold.models.RefreshToken;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.repositories.RefreshTokenRepository;
import com.usktea.plainold.utils.JwtUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@Service
public class IssueTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public IssueTokenService(RefreshTokenRepository refreshTokenRepository, JwtUtil jwtUtil) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    public TokenDto issue(Username username) {
        String accessToken = jwtUtil.encode(username.value());
        String refreshToken = jwtUtil.encode(UUID.randomUUID());

        RefreshToken refreshTokenEntity = RefreshToken.of(username, refreshToken);

        refreshTokenRepository.save(refreshTokenEntity);

        return new TokenDto(accessToken, refreshToken);
    }

    public TokenDto reissue(String token) {
        jwtUtil.decodeRefreshToken(token);

        RefreshToken refreshToken = refreshTokenRepository.findByNumber(token)
                .orElseThrow(RefreshTokenNotFound::new);

        String accessToken = refreshToken.getNextAccessToken(jwtUtil);
        String newRefreshToken = refreshToken.getNextVersion(jwtUtil);

        return new TokenDto(accessToken, newRefreshToken);
    }
}
