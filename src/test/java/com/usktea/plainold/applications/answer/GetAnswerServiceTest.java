package com.usktea.plainold.applications.answer;

import com.usktea.plainold.models.answer.Answer;
import com.usktea.plainold.repositories.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
@SuppressWarnings("unchecked")
class GetAnswerServiceTest {
    private AnswerRepository answerRepository;
    private GetAnswerService getAnswerService;

    @BeforeEach
    void setup() {
        answerRepository = mock(AnswerRepository.class);
        getAnswerService = new GetAnswerService(answerRepository);
    }

    @Test
    void answers() {
        Long id = 1L;
        List<Long> inquiryIds = List.of(id);

        given(answerRepository.findAll(any(Specification.class)))
                .willReturn(List.of(Answer.fake(id)));

        List<Answer> answers = getAnswerService.answers(inquiryIds);

        assertThat(answers).hasSize(1);
    }
}
