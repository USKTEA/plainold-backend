package com.usktea.plainold.applications;

import com.usktea.plainold.applications.inquiry.EditInquiryService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.EditInquiryRequest;
import com.usktea.plainold.exceptions.InquiryCannotBeEdited;
import com.usktea.plainold.exceptions.InquiryNotExists;
import com.usktea.plainold.exceptions.NotHaveEditInquiryAuthority;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.inquiry.Status;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class EditInquiryServiceTest {
    private GetUserService getUserService;
    private InquiryRepository inquiryRepository;
    private EditInquiryService editInquiryService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        inquiryRepository = mock(InquiryRepository.class);
        editInquiryService = new EditInquiryService(getUserService, inquiryRepository);
    }

    @Test
    void whenUserNotExist() {
        Username username = new Username("notExists@gmail.com");
        Long inquiryId = 1L;
        EditInquiryRequest editInquiryRequest = EditInquiryRequest.fake(inquiryId);

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class, () -> editInquiryService.edit(username, editInquiryRequest));
    }

    @Test
    void whenUserTryToEditNotHisOwnInquiry() {
        Username otherUser = new Username("otherUser@gmal.com");
        Username username = new Username("tjrxo1234@gmail.com");
        Long inquiryId = 1L;
        EditInquiryRequest editInquiryRequest = EditInquiryRequest.fake(inquiryId);

        given(getUserService.find(otherUser)).willReturn(Users.fake(otherUser));
        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(Inquiry.fake(Status.PENDING, username)));

        assertThrows(NotHaveEditInquiryAuthority.class,
                () -> editInquiryService.edit(otherUser, editInquiryRequest));
    }

    @Test
    void whenInquiryNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long inquiryId = 9_999_999L;
        EditInquiryRequest editInquiryRequest = EditInquiryRequest.fake(inquiryId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(inquiryRepository.findById(inquiryId)).willThrow(InquiryNotExists.class);

        assertThrows(InquiryNotExists.class,
                () -> editInquiryService.edit(username, editInquiryRequest));
    }

    @Test
    void whenInquiryCannotBeEdited() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long inquiryId = 1L;
        EditInquiryRequest editInquiryRequest = EditInquiryRequest.fake(inquiryId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(Inquiry.fake(Status.FINISHED, username)));

        assertThrows(InquiryCannotBeEdited.class,
                () -> editInquiryService.edit(username, editInquiryRequest));
    }

    @Test
    void whenEditSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long inquiryId = 1L;
        EditInquiryRequest editInquiryRequest = EditInquiryRequest.fake(inquiryId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(Inquiry.fake(Status.PENDING, username)));

        Long editedId = editInquiryService.edit(username, editInquiryRequest);

        assertThat(editedId).isEqualTo(inquiryId);
    }
}
