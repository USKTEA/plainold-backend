package com.usktea.plainold.models.inquiry;

import com.usktea.plainold.exceptions.InquiryCannotBeEdited;
import com.usktea.plainold.exceptions.NotHaveDeleteInquiryAuthority;
import com.usktea.plainold.exceptions.NotHaveEditInquiryAuthority;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class InquiryTest {
    @Test
    void whenInquiryTypeIsPublic() {
        Username username = new Username("tjrxo1234@gmail.com");

        Users user = Users.fake(username);
        Querist querist = Querist.fake(username);

        Inquiry inquiry = Inquiry.fake(InquiryType.PUBLIC, querist);

        InquiryView inquiryView = inquiry.toView(username, user.role());

        assertThat(inquiryView.title()).isNotEqualTo("비밀글입니다.");
        assertThat(inquiryView.content()).isNotEqualTo("비밀글입니다.");
    }

    @Test
    void whenInquiryTypeIsSecretAndUserHasNoAuthority() {
        Username username = new Username("tjrxo1234@gmail.com");
        Username otherUsername = new Username("rlatjrxo1234@gmail.com");

        Users user = Users.fake(otherUsername);
        Querist querist = Querist.fake(username);

        Inquiry inquiry = Inquiry.fake(InquiryType.SECRET, querist);

        InquiryView inquiryView = inquiry.toView(otherUsername, user.role());

        assertThat(inquiryView.title()).isEqualTo("비밀글입니다.");
        assertThat(inquiryView.content()).isEqualTo("비밀글입니다.");
    }

    @Test
    void whenOtherTryToEditInquiry() {
        Username username = new Username("tjrxo1234@gmail.com");
        Username otherUsername = new Username("rlatjrxo1234@gmail.com");
        Title title = new Title("이렇게 수정");
        Content content = new Content("저렇게 수정");

        Users user = Users.fake(otherUsername);

        Inquiry inquiry = Inquiry.fake(Status.PENDING, username);

        assertThrows(NotHaveEditInquiryAuthority.class,
                () -> inquiry.edit(otherUsername, user.role(), title, content));
    }

    @Test
    void whenInquiryStatusIsFinished() {
        Username username = new Username("tjrxo1234@gmail.com");
        Title title = new Title("이렇게 수정");
        Content content = new Content("저렇게 수정");

        Users user = Users.fake(username);

        Inquiry inquiry = Inquiry.fake(Status.FINISHED, username);

        assertThrows(InquiryCannotBeEdited.class, () -> inquiry.edit(
                user.username(), user.role(), title, content
        ));
    }

    @Test
    void whenInquiryStatusIsDeleted() {
        Username username = new Username("tjrxo1234@gmail.com");
        Title title = new Title("이렇게 수정");
        Content content = new Content("저렇게 수정");

        Users user = Users.fake(username);

        Inquiry inquiry = Inquiry.fake(Status.DELETED, username);

        assertThrows(InquiryCannotBeEdited.class, () -> inquiry.edit(
                user.username(), user.role(), title, content
        ));
    }

    @Test
    void whenEditInquirySuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        Users user = Users.fake(username);
        Title title = new Title("이렇게 수정");
        Content content = new Content("저렇게 수정");

        Inquiry inquiry = Inquiry.fake(Status.PENDING, username);

        inquiry.edit(user.username(), user.role(), title, content);

        assertThat(inquiry.title()).isEqualTo(title);
        assertThat(inquiry.content()).isEqualTo(content);
    }

    @Test
    void whenAdminTryToEditInquiry() {
        Username username = new Username("tjrxo1234@gmail.com");
        Users admin = Users.fake(Role.ADMIN);
        Title title = new Title("이렇게 수정");
        Content content = new Content("저렇게 수정");

        Inquiry inquiry = Inquiry.fake(Status.PENDING, username);

        inquiry.edit(admin.username(), admin.role(), title, content);

        assertThat(inquiry.title()).isEqualTo(title);
        assertThat(inquiry.content()).isEqualTo(content);
    }

    @Test
    void whenDeleteSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");

        Users user = Users.fake(username);

        Inquiry inquiry = Inquiry.fake(Status.PENDING, username);

        assertThat(inquiry.status()).isEqualTo(Status.PENDING);

        inquiry.delete(user.username(), user.role());

        assertThat(inquiry.status()).isEqualTo(Status.DELETED);
    }

    @Test
    void whenDeleteFailed() {
        Username username = new Username("tjrxo1234@gmail.com");
        Username otherUser = new Username("otherUser@gmail.com");

        Users user = Users.fake(otherUser);

        Inquiry inquiry = Inquiry.fake(Status.PENDING, username);

        assertThrows(NotHaveDeleteInquiryAuthority.class,
                () -> inquiry.delete(user.username(), user.role()));
    }

    @Test
    void whenAdminDeleteInquiry() {
        Username username = new Username("tjrxo1234@gmail.com");

        Users admin = Users.fake(Role.ADMIN);

        Inquiry inquiry = Inquiry.fake(Status.PENDING, username);

        inquiry.delete(admin.username(), admin.role());

        assertThat(inquiry.status()).isEqualTo(Status.DELETED);
    }
}
