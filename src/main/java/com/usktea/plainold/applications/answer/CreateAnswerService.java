package com.usktea.plainold.applications.answer;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.CreateAnswerRequest;
import com.usktea.plainold.exceptions.CannotAnswerToDeletedInquiry;
import com.usktea.plainold.exceptions.InquiryNotExists;
import com.usktea.plainold.exceptions.NotHaveCreateAnswerAuthority;
import com.usktea.plainold.models.answer.Answer;
import com.usktea.plainold.models.answer.Answerer;
import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.AnswerRepository;
import com.usktea.plainold.repositories.InquiryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreateAnswerService {
    private GetUserService getUserService;
    private InquiryRepository inquiryRepository;
    private AnswerRepository answerRepository;

    public CreateAnswerService(GetUserService getUserService,
                               InquiryRepository inquiryRepository,
                               AnswerRepository answerRepository) {
        this.getUserService = getUserService;
        this.inquiryRepository = inquiryRepository;
        this.answerRepository = answerRepository;
    }

    public Long create(Username username, CreateAnswerRequest createAnswerRequest) {
        Users user = getUserService.find(username);

        checkIsAdmin(user);

        Inquiry inquiry = inquiryRepository.findById(createAnswerRequest.inquiryId())
                .orElseThrow(InquiryNotExists::new);

        checkIfInquiryIsDeleted(inquiry);

        Answer answer = new Answer(
                inquiry.id(),
                new Answerer(user.username(), user.nickname()),
                createAnswerRequest.content()
        );

        Answer saved = answerRepository.save(answer);

        return saved.id();
    }

    private void checkIfInquiryIsDeleted(Inquiry inquiry) {
        if (inquiry.isDeleted()) {
            throw new CannotAnswerToDeletedInquiry();
        }
    }

    private void checkIsAdmin(Users user) {
        if (!user.isAdmin()) {
            throw new NotHaveCreateAnswerAuthority();
        }
    }
}
