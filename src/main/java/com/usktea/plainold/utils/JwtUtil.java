package com.usktea.plainold.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    private final Algorithm algorithm;
    private final Long ACCESS_TOKEN_VALIDATION_SECOND = 1000L * 60 * 30;
    private final Long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 24 * 14;

    public JwtUtil(String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String encode(String username) {
        return JWT.create()
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDATION_SECOND))
                .sign(algorithm);
    }

    public String encode(UUID uuid) {
        return JWT.create()
                .withClaim("uuid", uuid.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDATION_SECOND))
                .sign(algorithm);
    }

    public String decode(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        return decoded.getClaim("username").asString();
    }

    public String decodeRefreshToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        return decoded.getClaim("uuid").asString();
    }
}
