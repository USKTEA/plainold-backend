package com.usktea.plainold.applications.user;

import com.usktea.plainold.dtos.CreateUserRequest;
import com.usktea.plainold.exceptions.UsernameAlreadyInUse;
import com.usktea.plainold.models.user.Nickname;
import com.usktea.plainold.models.user.Password;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreateUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Username create(CreateUserRequest createUserRequest) {
        if (userRepository.existsByUsername(createUserRequest.username())) {
            throw new UsernameAlreadyInUse();
        }

        Users user = new Users(createUserRequest.username(), createUserRequest.nickname());

        user.changePassword(createUserRequest.password(), passwordEncoder);

        Users saved = userRepository.save(user);

        return saved.username();
    }
}
