package com.usktea.plainold.applications;

import com.usktea.plainold.applications.answer.DeleteAnswerService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.AnswerNotFound;
import com.usktea.plainold.exceptions.NotHaveDeleteAnswerAuthority;
import com.usktea.plainold.models.answer.Answer;
import com.usktea.plainold.models.answer.Status;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class DeleteAnswerServiceTest {
    private GetUserService getUserService;
    private AnswerRepository answerRepository;
    private DeleteAnswerService deleteAnswerService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        answerRepository = mock(AnswerRepository.class);
        deleteAnswerService = new DeleteAnswerService(getUserService, answerRepository);
    }

    @Test
    void whenNotAdminTryToDelete() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long answerId = 1L;
        Users user = Users.fake(username, Role.MEMBER);

        given(getUserService.find(username)).willReturn(user);
        given(answerRepository.findById(answerId))
                .willReturn(Optional.of(Answer.fake(Status.ACTIVE)));

        assertThrows(NotHaveDeleteAnswerAuthority.class,
                () -> deleteAnswerService.delete(username, answerId));
    }

    @Test
    void whenAnswerNotExists() {
        Users admin = Users.fake(Role.ADMIN);
        Long answerId = 1L;

        given(getUserService.find(admin.username())).willReturn(admin);
        given(answerRepository.findById(answerId)).willThrow(AnswerNotFound.class);

        assertThrows(AnswerNotFound.class,
                () -> deleteAnswerService.delete(admin.username(), answerId));
    }

    @Test
    void whenDeleteSuccess() {
        Users admin = Users.fake(Role.ADMIN);
        Long answerId = 1L;

        given(getUserService.find(admin.username())).willReturn(admin);
        given(answerRepository.findById(answerId))
                .willReturn(Optional.of(Answer.fake(Status.ACTIVE)));

        Long deleted = deleteAnswerService.delete(admin.username(), answerId);

        assertThat(deleted).isNotNull();
    }
}
