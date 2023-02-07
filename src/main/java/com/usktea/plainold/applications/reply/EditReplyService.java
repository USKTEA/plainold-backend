package com.usktea.plainold.applications.reply;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.EditReplyRequest;
import com.usktea.plainold.exceptions.ReplyNotExists;
import com.usktea.plainold.exceptions.ReviewNotFound;
import com.usktea.plainold.models.reply.Reply;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.ReplyRepository;
import com.usktea.plainold.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EditReplyService {
    private final GetUserService getUserService;
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;

    public EditReplyService(GetUserService getUserService,
                            ReviewRepository reviewRepository,
                            ReplyRepository replyRepository) {
        this.getUserService = getUserService;
        this.reviewRepository = reviewRepository;
        this.replyRepository = replyRepository;
    }

    public Long edit(Username username, EditReplyRequest editReplyRequest) {
        Users user = findUser(username);
        Reply reply = findReply(editReplyRequest.replyId());

        checkReviewIsExists(editReplyRequest.reviewId());

        reply.edit(user.username(), editReplyRequest.comment());

        return reply.id();
    }

    private Reply findReply(Long replyId) {
        return replyRepository.findById(replyId).orElseThrow(ReplyNotExists::new);
    }

    private void checkReviewIsExists(Long reviewId) {
        reviewRepository.findById(reviewId).orElseThrow(ReviewNotFound::new);
    }

    private Users findUser(Username username) {
        return getUserService.find(username);
    }
}
