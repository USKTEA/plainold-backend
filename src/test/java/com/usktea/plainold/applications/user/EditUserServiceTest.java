package com.usktea.plainold.applications.user;

import com.usktea.plainold.dtos.EditUserRequest;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.exceptions.UsernameNotMatch;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class EditUserServiceTest {
    private UserRepository userRepository;
    private EditUserService editUserService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        editUserService = new EditUserService(userRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("NotExists@gmail.com");
        EditUserRequest editUserRequest = EditUserRequest.fake(username);

        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        assertThrows(UserNotExists.class,
                () -> editUserService.edit(username, editUserRequest));
    }

    @Test
    void whenUsernameNotMatch() {
        Username username = new Username("tjrxo1234@gmail.com");
        Username otherUsername = new Username("notTjrxo1234@gmail.com");

        EditUserRequest editUserRequest = EditUserRequest.fake(otherUsername);

        given(userRepository.findByUsername(username))
                .willReturn(Optional.of(Users.fake(username)));

        assertThrows(UsernameNotMatch.class,
                () -> editUserService.edit(username, editUserRequest));
    }

    @Test
    void whenEditUserSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        EditUserRequest editUserRequest = EditUserRequest.fake(username);

        given(userRepository.findByUsername(username))
                .willReturn(Optional.of(Users.fake(username)));

        Username edited = editUserService.edit(username, editUserRequest);

        assertThat(edited).isEqualTo(username);
    }
}
