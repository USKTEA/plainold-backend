package com.usktea.plainold.applications;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class GetUserServiceTest {
    private UserRepository userRepository;
    private GetUserService getUserService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        getUserService = new GetUserService(userRepository);
    }

    @Test
    void whenUserExists() {
        Username username = new Username("tjrxo1234@gmail.com");

        given(userRepository.findByUsername(username))
                .willReturn(Optional.of(Users.fake(username)));

        assertDoesNotThrow(() -> getUserService.find(username));
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");

        given(userRepository.findByUsername(username))
                .willReturn(Optional.empty());

        assertThrows(UserNotExists.class,
                () -> getUserService.find(username));
    }
}
