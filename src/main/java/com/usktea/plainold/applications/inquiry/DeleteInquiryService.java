package com.usktea.plainold.applications.inquiry;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.InquiryNotExists;
import com.usktea.plainold.models.answer.Answer;
import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.AnswerRepository;
import com.usktea.plainold.repositories.InquiryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DeleteInquiryService {
    private final GetUserService getUserService;
    private final InquiryRepository inquiryRepository;
    private final AnswerRepository answerRepository;

    public DeleteInquiryService(GetUserService getUserService,
                                InquiryRepository inquiryRepository,
                                AnswerRepository answerRepository) {
        this.getUserService = getUserService;
        this.inquiryRepository = inquiryRepository;
        this.answerRepository = answerRepository;
    }

    public Long delete(Username username, Long id) {
        Users user = getUserService.find(username);

        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(InquiryNotExists::new);

        inquiry.delete(user.username(), user.role());

        List<Answer> answers = answerRepository.findAllByInquiryId(id);

        if (!answers.isEmpty()) {
            deleteAnswers(answers);
        }

        return inquiry.id();
    }

    private void deleteAnswers(List<Answer> answers) {
        answers.stream().forEach(Answer::delete);
    }
}
