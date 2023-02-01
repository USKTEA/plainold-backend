package com.usktea.plainold.applications;

import com.usktea.plainold.applications.inquiry.DeleteInquiryService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.InquiryNotExists;
import com.usktea.plainold.exceptions.NotHaveDeleteInquiryAuthority;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.inquiry.Status;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.AnswerRepository;
import com.usktea.plainold.repositories.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class DeleteInquiryServiceTest {
    private GetUserService getUserService;
    private InquiryRepository inquiryRepository;
    private AnswerRepository answerRepository;
    private DeleteInquiryService deleteInquiryService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        inquiryRepository = mock(InquiryRepository.class);
        answerRepository = mock(AnswerRepository.class);
        deleteInquiryService = new DeleteInquiryService(
                getUserService, inquiryRepository, answerRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");
        Long id = 1L;

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class,
                () -> deleteInquiryService.delete(username, id));
    }

    @Test
    void whenUserTryToDeleteNotHisOwnInquiry() {
        Username otherUser = new Username("otherUser@gmail.com");
        Username username = new Username("tjrxo1234@gmail.com");
        Long id = 1L;

        given(getUserService.find(otherUser)).willReturn(Users.fake(otherUser));
        given(inquiryRepository.findById(id))
                .willReturn(Optional.of(Inquiry.fake(Status.PENDING, username)));

        assertThrows(NotHaveDeleteInquiryAuthority.class,
                () -> deleteInquiryService.delete(otherUser, id));
    }

    @Test
    void whenInquiryNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long id = 9_999_999L;

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(inquiryRepository.findById(id))
                .willThrow(InquiryNotExists.class);

        assertThrows(InquiryNotExists.class,
                () -> deleteInquiryService.delete(username, id));
    }

    @Test
    void whenAdminDeleteInquiry() {
        Username username = new Username("tjrxo1234@gmail.com");
        Users admin = Users.fake(Role.ADMIN);
        Long id = 1L;

        given(getUserService.find(admin.username())).willReturn(admin);
        given(inquiryRepository.findById(id))
                .willReturn(Optional.of(Inquiry.fake(Status.PENDING, username)));
        given(answerRepository.findAllByInquiryId(id)).willReturn(List.of());

        Long deleted = deleteInquiryService.delete(admin.username(), id);

        assertThat(deleted).isEqualTo(id);
    }

    @Test
    void whenDeleteSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long id = 1L;

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(inquiryRepository.findById(id))
                .willReturn(Optional.of(Inquiry.fake(Status.PENDING, username)));
        given(answerRepository.findAllByInquiryId(id)).willReturn(List.of());

        Long deleted = deleteInquiryService.delete(username, id);
        assertThat(deleted).isEqualTo(id);
    }
}
