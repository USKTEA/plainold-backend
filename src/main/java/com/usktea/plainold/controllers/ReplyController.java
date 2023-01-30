package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.CreateReplyService;
import com.usktea.plainold.applications.DeleteReplyService;
import com.usktea.plainold.applications.EditReplyService;
import com.usktea.plainold.applications.GetReplyService;
import com.usktea.plainold.dtos.CreateReplyRequest;
import com.usktea.plainold.dtos.CreateReplyRequestDto;
import com.usktea.plainold.dtos.CreateReplyResultDto;
import com.usktea.plainold.dtos.DeleteReplyResultDto;
import com.usktea.plainold.dtos.EditReplyRequest;
import com.usktea.plainold.dtos.EditReplyRequestDto;
import com.usktea.plainold.dtos.EditReplyResultDto;
import com.usktea.plainold.dtos.RepliesDto;
import com.usktea.plainold.exceptions.CreateReplyFailed;
import com.usktea.plainold.exceptions.DeleteReplyFailed;
import com.usktea.plainold.exceptions.EditReplyFailed;
import com.usktea.plainold.exceptions.ReplierNotMatch;
import com.usktea.plainold.models.reply.Reply;
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
@RequestMapping("replies")
public class ReplyController {
    private final GetReplyService getReplyService;
    private final CreateReplyService createReplyService;
    private final EditReplyService editReplyService;
    private final DeleteReplyService deleteReplyService;

    public ReplyController(GetReplyService getReplyService,
                           CreateReplyService createReplyService,
                           EditReplyService editReplyService,
                           DeleteReplyService deleteReplyService) {
        this.getReplyService = getReplyService;
        this.createReplyService = createReplyService;
        this.editReplyService = editReplyService;
        this.deleteReplyService = deleteReplyService;
    }

    @GetMapping
    public RepliesDto replies(
            @RequestParam("reviewIds") String ids,
            HttpServletResponse response
    ) {
        List<Long> reviewIds = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Reply> replies = getReplyService.list(reviewIds);

        if (replies.isEmpty()) {
            response.setStatus(204);
        }

        return new RepliesDto(replies.stream()
                .map(Reply::toDto)
                .collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateReplyResultDto create(
            @RequestAttribute Username username,
            @Valid @RequestBody CreateReplyRequestDto createReplyRequestDto
    ) {
        try {
            CreateReplyRequest createReplyRequest =
                    CreateReplyRequest.of(createReplyRequestDto);

            Long id = createReplyService.create(username, createReplyRequest);

            return new CreateReplyResultDto(id);
        } catch (Exception exception) {
            throw new CreateReplyFailed(exception.getMessage());
        }
    }

    @PatchMapping
    public EditReplyResultDto edit(
            @RequestAttribute Username username,
            @Valid @RequestBody EditReplyRequestDto editReplyRequestDto
    ) {
        try {
            EditReplyRequest editReplyRequest = EditReplyRequest.of(editReplyRequestDto);

            Long editedId = editReplyService.edit(username, editReplyRequest);

            return new EditReplyResultDto(editedId);
        } catch (ReplierNotMatch replierNotMatch) {
            throw new ReplierNotMatch();
        } catch (Exception exception) {
            throw new EditReplyFailed(exception.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public DeleteReplyResultDto delete(
            @RequestAttribute Username username,
            @PathVariable Long id
    ) {
        try {
            Long deletedId = deleteReplyService.delete(username, id);

            return new DeleteReplyResultDto(deletedId);
        } catch (ReplierNotMatch replierNotMatch) {
            throw new ReplierNotMatch();
        } catch (Exception exception) {
            throw new DeleteReplyFailed(exception.getMessage());
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidCreateReplyRequest() {
        return "잘못된 요청입니다";
    }

    @ExceptionHandler(ReplierNotMatch.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String replierNotMatch(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(EditReplyFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String editReplyFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CreateReplyFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String createReplyFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(DeleteReplyFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String deleteReplyFail(Exception exception) {
        return exception.getMessage();
    }
}
