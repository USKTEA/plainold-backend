package com.usktea.plainold.applications;

import com.usktea.plainold.applications.user.CountUserService;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class CountUserServiceTest {
    private UserRepository userRepository;
    private CountUserService countUserService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        countUserService = new CountUserService(userRepository);
    }

    @Test
    void count() {
        Username username = new Username("tjrxo1234@gmail.com");

        List<Users> users = List.of(Users.fake(username));

        given(userRepository.findAllByUsername(username)).willReturn(users);

        Integer counts = countUserService.count(username);

        assertThat(counts).isEqualTo(users.size());
    }
}
