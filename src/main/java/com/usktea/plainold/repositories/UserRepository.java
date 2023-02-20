package com.usktea.plainold.repositories;

import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Username> {
    Optional<Users> findByUsername(Username username);

    List<Users> findAllByUsername(Username username);

    Boolean existsByUsername(Username username);
}
