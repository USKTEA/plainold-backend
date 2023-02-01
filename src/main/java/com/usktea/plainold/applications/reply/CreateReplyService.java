package com.usktea.plainold.applications.reply;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.CreateReplyRequest;
import com.usktea.plainold.exceptions.ReviewNotFound;
import com.usktea.plainold.models.reply.Replier;
import com.usktea.plainold.models.reply.Reply;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.ReplyRepository;
import com.usktea.plainold.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class CreateReplyService {
    private final GetUserService getUserService;
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;

    public CreateReplyService(GetUserService getUserService,
                              ReviewRepository reviewRepository,
                              ReplyRepository replyRepository) {
        this.getUserService = getUserService;
        this.reviewRepository = reviewRepository;
        this.replyRepository = replyRepository;
    }

    public Long create(Username username, CreateReplyRequest createReplyRequest) {
        Users user = getUserService.find(username);
        Long reviewId = createReplyRequest.reviewId();

        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFound::new);

        Reply reply = new Reply(
                review.id(),
                createReplyRequest.parent(),
                createReplyRequest.comment(),
                new Replier(
                        user.username(),
                        user.nickname()
                )
        );

        Reply saved = replyRepository.save(reply);

        return saved.id();
    }
}
