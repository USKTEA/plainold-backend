package com.usktea.plainold.applications;

import com.usktea.plainold.applications.answer.CreateAnswerService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.CreateAnswerRequest;
import com.usktea.plainold.exceptions.CannotAnswerToDeletedInquiry;
import com.usktea.plainold.exceptions.InquiryNotExists;
import com.usktea.plainold.exceptions.NotHaveCreateAnswerAuthority;
import com.usktea.plainold.models.answer.Answer;
import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.inquiry.Status;
import com.usktea.plainold.models.order.OrderStatus;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.AnswerRepository;
import com.usktea.plainold.repositories.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class CreateAnswerServiceTest {
    private GetUserService getUserService;
    private InquiryRepository inquiryRepository;
    private AnswerRepository answerRepository;
    private CreateAnswerService createAnswerService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        inquiryRepository = mock(InquiryRepository.class);
        answerRepository = mock(AnswerRepository.class);
        createAnswerService = new CreateAnswerService(
                getUserService, inquiryRepository, answerRepository);
    }

    @Test
    void whenNotAdminTryToCreateAnswer() {
        Username username = new Username("notAdmin@gmail.com");
        Users member = Users.fake(username, Role.MEMBER);
        Long inquiryId = 1L;
        CreateAnswerRequest createAnswerRequest = CreateAnswerRequest.fake(inquiryId);

        given(getUserService.find(username)).willReturn(member);

        assertThrows(NotHaveCreateAnswerAuthority.class,
                () -> createAnswerService.create(username, createAnswerRequest));
    }

    @Test
    void whenInquiryNotExists() {
        Users admin = Users.fake(Role.ADMIN);
        Long inquiryId = 1L;
        CreateAnswerRequest createAnswerRequest = CreateAnswerRequest.fake(inquiryId);

        given(getUserService.find(admin.username())).willReturn(admin);
        given(inquiryRepository.findById(inquiryId)).willThrow(InquiryNotExists.class);

        assertThrows(InquiryNotExists.class,
                () -> createAnswerService.create(admin.username(), createAnswerRequest));
    }

    @Test
    void whenInquiryIsDeleted() {
        Username otherUser = new Username("tjrxo1234@gmail.com");

        Users admin = Users.fake(Role.ADMIN);
        Long inquiryId = 1L;
        CreateAnswerRequest createAnswerRequest = CreateAnswerRequest.fake(inquiryId);

        given(getUserService.find(admin.username())).willReturn(admin);
        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(Inquiry.fake(Status.DELETED, otherUser)));

        assertThrows(CannotAnswerToDeletedInquiry.class,
                () -> createAnswerService.create(admin.username(), createAnswerRequest));
    }

    @Test
    void whenCreateSuccess() {
        Username otherUser = new Username("tjrxo1234@gmail.com");

        Users admin = Users.fake(Role.ADMIN);
        Long inquiryId = 1L;
        CreateAnswerRequest createAnswerRequest = CreateAnswerRequest.fake(inquiryId);

        given(getUserService.find(admin.username())).willReturn(admin);
        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(Inquiry.fake(Status.PENDING, otherUser)));
        given(answerRepository.save(any())).willReturn(Answer.fake(inquiryId));

        Long id = createAnswerService.create(admin.username(), createAnswerRequest);

        assertThat(id).isNotNull();

        verify(answerRepository).save(any(Answer.class));
    }
}
