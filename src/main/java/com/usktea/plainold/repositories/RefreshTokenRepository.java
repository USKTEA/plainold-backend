package com.usktea.plainold.repositories;

import com.usktea.plainold.models.RefreshToken;
import com.usktea.plainold.models.user.Username;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Username> {
    Optional<RefreshToken> findByNumber(String number);
}
