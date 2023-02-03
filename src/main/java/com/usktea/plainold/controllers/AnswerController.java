package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.answer.DeleteAnswerService;
import com.usktea.plainold.applications.answer.CreateAnswerService;
import com.usktea.plainold.applications.answer.EditAnswerService;
import com.usktea.plainold.applications.answer.GetAnswerService;
import com.usktea.plainold.dtos.AnswersDto;
import com.usktea.plainold.dtos.CreateAnswerRequest;
import com.usktea.plainold.dtos.CreateAnswerRequestDto;
import com.usktea.plainold.dtos.CreateAnswerResultDto;
import com.usktea.plainold.dtos.DeleteAnswerResultDto;
import com.usktea.plainold.dtos.EditAnswerRequest;
import com.usktea.plainold.dtos.EditAnswerRequestDto;
import com.usktea.plainold.dtos.EditAnswerResultDto;
import com.usktea.plainold.exceptions.CreateAnswerFailed;
import com.usktea.plainold.exceptions.DeleteAnswerFailed;
import com.usktea.plainold.exceptions.EditAnswerFailed;
import com.usktea.plainold.exceptions.NotHaveCreateAnswerAuthority;
import com.usktea.plainold.exceptions.NotHaveDeleteAnswerAuthority;
import com.usktea.plainold.exceptions.NotHaveEditAnswerAuthority;
import com.usktea.plainold.models.answer.Answer;
import com.usktea.plainold.models.user.Username;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("answers")
public class AnswerController {
    private final GetAnswerService getAnswerService;
    private final CreateAnswerService createAnswerService;
    private final EditAnswerService editAnswerService;
    private final DeleteAnswerService deleteAnswerService;

    public AnswerController(GetAnswerService getAnswerService,
                            CreateAnswerService createAnswerService,
                            EditAnswerService editAnswerService,
                            DeleteAnswerService deleteAnswerService) {
        this.getAnswerService = getAnswerService;
        this.createAnswerService = createAnswerService;
        this.editAnswerService = editAnswerService;
        this.deleteAnswerService = deleteAnswerService;
    }

    @GetMapping
    public AnswersDto answers(
            @RequestParam String inquiryIds,
            HttpServletResponse response
    ) {
        List<Long> ids = Arrays.stream(inquiryIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Answer> answers = getAnswerService.answers(ids);

        if (answers.isEmpty()) {
            response.setStatus(204);
        }

        return new AnswersDto(answers.stream()
                .map(Answer::toDto)
                .collect(Collectors.toList())
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAnswerResultDto create(
            @RequestAttribute Username username,
            @Valid @RequestBody CreateAnswerRequestDto createAnswerRequestDto
    ) {
        try {
            CreateAnswerRequest createAnswerRequest = CreateAnswerRequest.of(createAnswerRequestDto);

            Long created = createAnswerService.create(username, createAnswerRequest);

            return new CreateAnswerResultDto(created);
        } catch (NotHaveCreateAnswerAuthority notHaveCreateAnswerAuthority) {
            throw new NotHaveCreateAnswerAuthority();
        } catch (Exception exception) {
            throw new CreateAnswerFailed(exception.getMessage());
        }
    }

    @PatchMapping
    public EditAnswerResultDto edit(
            @RequestAttribute Username username,
            @Valid @RequestBody EditAnswerRequestDto editAnswerRequestDto
    ) {
        try {
            EditAnswerRequest editAnswerRequest = EditAnswerRequest.of(editAnswerRequestDto);

            Long edited = editAnswerService.edit(username, editAnswerRequest);

            return new EditAnswerResultDto(edited);
        } catch (NotHaveEditAnswerAuthority notHaveEditAnswerAuthority) {
            throw new NotHaveEditAnswerAuthority();
        } catch (Exception exception) {
            throw new EditAnswerFailed(exception.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public DeleteAnswerResultDto delete(
            @RequestAttribute Username username,
            @PathVariable Long id
    ) {
        try {
            Long deleted = deleteAnswerService.delete(username, id);

            return new DeleteAnswerResultDto(deleted);
        } catch (NotHaveDeleteAnswerAuthority notHaveDeleteAnswerAuthority) {
            throw new NotHaveDeleteAnswerAuthority();
        } catch (Exception exception) {
            throw new DeleteAnswerFailed(exception.getMessage());
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidRequestInformation() {
        return "요청 정보가 정확하지 않습니다";
    }

    @ExceptionHandler(NotHaveCreateAnswerAuthority.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String notHaveCreateAnswerAuthority(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(NotHaveEditAnswerAuthority.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String notHaveEditAnswerAuthority(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(NotHaveDeleteAnswerAuthority.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String notHaveDeleteAnswerAuthority(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CreateAnswerFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String createAnswerFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(EditAnswerFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String editAnswerFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(DeleteAnswerFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String deleteAnswerFail(Exception exception) {
        return exception.getMessage();
    }
}
