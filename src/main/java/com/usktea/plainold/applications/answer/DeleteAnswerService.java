package com.usktea.plainold.applications.answer;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.AnswerNotFound;
import com.usktea.plainold.models.answer.Answer;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.AnswerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DeleteAnswerService {
    private final GetUserService getUserService;
    private final AnswerRepository answerRepository;

    public DeleteAnswerService(GetUserService getUserService, AnswerRepository answerRepository) {
        this.getUserService = getUserService;
        this.answerRepository = answerRepository;
    }

    public Long delete(Username username, Long id) {
        Users admin = getUserService.find(username);
        Answer answer = answerRepository.findById(id)
                .orElseThrow(AnswerNotFound::new);

        answer.delete(admin.role());

        return answer.id();
    }
}
