package com.usktea.plainold.applications.answer;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.EditAnswerRequest;
import com.usktea.plainold.exceptions.AnswerCannotBeEdited;
import com.usktea.plainold.exceptions.AnswerNotFound;
import com.usktea.plainold.exceptions.NotHaveEditAnswerAuthority;
import com.usktea.plainold.models.answer.Answer;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.AnswerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EditAnswerService {
    private final GetUserService getUserService;
    private final AnswerRepository answerRepository;

    public EditAnswerService(GetUserService getUserService, AnswerRepository answerRepository) {
        this.getUserService = getUserService;
        this.answerRepository = answerRepository;
    }

    public Long edit(Username username, EditAnswerRequest editAnswerRequest) {
        Users user = getUserService.find(username);

        Answer answer = answerRepository.findById(editAnswerRequest.id())
                .orElseThrow(AnswerNotFound::new);

        answer.edit(user.role(), editAnswerRequest.content());

        return answer.id();
    }
}
