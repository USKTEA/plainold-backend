package com.usktea.plainold.repositories;

import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Username> {
    Optional<Users> findByUsername(Username username);
}
