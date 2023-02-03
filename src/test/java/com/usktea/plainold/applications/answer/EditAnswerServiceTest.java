package com.usktea.plainold.applications.answer;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.EditAnswerRequest;
import com.usktea.plainold.exceptions.AnswerCannotBeEdited;
import com.usktea.plainold.exceptions.AnswerNotFound;
import com.usktea.plainold.exceptions.NotHaveEditAnswerAuthority;
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
class EditAnswerServiceTest {
    private GetUserService getUserService;
    private AnswerRepository answerRepository;
    private EditAnswerService editAnswerService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        answerRepository = mock(AnswerRepository.class);
        editAnswerService = new EditAnswerService(getUserService, answerRepository);
    }

    @Test
    void whenNotAdminTryToEditAnswer() {
        Username username = new Username("tjrxo1234@gmail.com");
        Users member = Users.fake(username, Role.MEMBER);
        Long answerId = 1L;

        EditAnswerRequest editAnswerRequest = EditAnswerRequest.fake(answerId);

        given(getUserService.find(username)).willReturn(member);
        given(answerRepository.findById(answerId))
                .willReturn(Optional.of(Answer.fake(Status.ACTIVE)));

        assertThrows(NotHaveEditAnswerAuthority.class,
                () -> editAnswerService.edit(username, editAnswerRequest));
    }

    @Test
    void whenAnswerNotExists() {
        Users admin = Users.fake(Role.ADMIN);
        Long answerId = 9_999_999L;

        EditAnswerRequest editAnswerRequest = EditAnswerRequest.fake(answerId);

        given(getUserService.find(admin.username())).willReturn(admin);
        given(answerRepository.findById(answerId)).willThrow(AnswerNotFound.class);

        assertThrows(AnswerNotFound.class,
                () -> editAnswerService.edit(admin.username(), editAnswerRequest));
    }

    @Test
    void whenAnswerIsDeleted() {
        Users admin = Users.fake(Role.ADMIN);
        Long answerId = 1L;

        EditAnswerRequest editAnswerRequest = EditAnswerRequest.fake(answerId);

        given(getUserService.find(admin.username())).willReturn(admin);
        given(answerRepository.findById(answerId))
                .willReturn(Optional.of(Answer.fake(Status.DELETED)));

        assertThrows(AnswerCannotBeEdited.class,
                () -> editAnswerService.edit(admin.username(), editAnswerRequest));
    }

    @Test
    void whenEditSuccess() {
        Users admin = Users.fake(Role.ADMIN);
        Long answerId = 1L;

        EditAnswerRequest editAnswerRequest = EditAnswerRequest.fake(answerId);

        given(getUserService.find(admin.username())).willReturn(admin);
        given(answerRepository.findById(answerId))
                .willReturn(Optional.of(Answer.fake(Status.ACTIVE)));

        Long id = editAnswerService.edit(admin.username(), editAnswerRequest);

        assertThat(id).isNotNull();
    }
}
