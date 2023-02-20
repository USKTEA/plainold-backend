package com.usktea.plainold.applications;

import com.usktea.plainold.applications.user.CreateUserService;
import com.usktea.plainold.dtos.CreateUserRequest;
import com.usktea.plainold.exceptions.UsernameAlreadyInUse;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class CreateUserServiceTest {
    private UserRepository userRepository;
    private CreateUserService createUserService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = new Argon2PasswordEncoder();
        createUserService = new CreateUserService(userRepository, passwordEncoder);
    }

    @Test
    void whenCreateUserSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");

        Users user = Users.fake(username);

        CreateUserRequest createUserRequest = CreateUserRequest.fake(username);

        given(userRepository.save(user)).willReturn(user);

        Username created = createUserService.create(createUserRequest);

        assertThat(created).isEqualTo(user.username());

        verify(userRepository).save(user);
    }

    @Test
    void whenUsernameAlreadyInUse() {
        Username username = new Username("alreadyInUse@gmail.com");

        given(userRepository.existsByUsername(username))
                .willReturn(true);

        CreateUserRequest createUserRequest = CreateUserRequest.fake(username);

        assertThrows(UsernameAlreadyInUse.class,
                () -> createUserService.create(createUserRequest));
    }
}
