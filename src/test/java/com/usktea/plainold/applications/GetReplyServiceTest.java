package com.usktea.plainold.applications;

import com.usktea.plainold.models.reply.Reply;
import com.usktea.plainold.repositories.ReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
@SuppressWarnings("unchecked")
class GetReplyServiceTest {
    private ReplyRepository replyRepository;
    private GetReplyService getReplyService;

    @BeforeEach
    void setup() {
        replyRepository = mock(ReplyRepository.class);
        getReplyService = new GetReplyService(replyRepository);
    }

    @Test
    void list() {
        given(replyRepository.findAll(any(Specification.class)))
                .willReturn(List.of(Reply.fake(1L)));

        List<Long> reviewIds = List.of(1L);

        List<Reply> replies = getReplyService.list(reviewIds);

        assertThat(replies).hasSize(1);
    }
}
