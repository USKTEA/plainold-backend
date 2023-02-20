package com.usktea.plainold.applications.user;

import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CountUserService {
    private final UserRepository userRepository;

    public CountUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer count(Username username) {
        List<Users> users = userRepository.findAllByUsername(username);

        return users.size();
    }
}
