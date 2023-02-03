package com.usktea.plainold.applications.answer;

import com.usktea.plainold.models.answer.Answer;
import com.usktea.plainold.models.answer.Status;
import com.usktea.plainold.repositories.AnswerRepository;
import com.usktea.plainold.specifications.AnswerSpecification;
import com.usktea.plainold.specifications.ReplySpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class GetAnswerService {
    private final AnswerRepository answerRepository;

    public GetAnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> answers(List<Long> inquiryIds) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");

        Specification<Answer> specification =
                AnswerSpecification.withInquiryIds(inquiryIds, sort)
                        .and(AnswerSpecification.withStatus(Status.ACTIVE));

        List<Answer> answers = answerRepository.findAll(specification);

        return answers;
    }
}
