package com.usktea.plainold.applications.user;

import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class GetUserService {
    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users find(Username username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(UserNotExists::new);

        return user;
    }
}
