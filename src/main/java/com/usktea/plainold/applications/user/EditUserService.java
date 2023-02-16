package com.usktea.plainold.applications.user;

import com.usktea.plainold.dtos.EditUserRequest;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EditUserService {
    private final UserRepository userRepository;

    public EditUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Username edit(Username username, EditUserRequest editUserRequest) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(UserNotExists::new);

        user.update(editUserRequest);

        return user.username();
    }
}
