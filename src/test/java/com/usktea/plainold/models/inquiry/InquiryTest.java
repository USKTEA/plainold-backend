package com.usktea.plainold.models.inquiry;

import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

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
}
