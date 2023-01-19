package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.EditReviewRequest;
import com.usktea.plainold.exceptions.ReviewNotFound;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class EditReviewService {
    private final GetUserService getUserService;
    private final ReviewRepository reviewRepository;

    public EditReviewService(GetUserService getUserService, ReviewRepository reviewRepository) {
        this.getUserService = getUserService;
        this.reviewRepository = reviewRepository;
    }

    public Review edit(Username username, EditReviewRequest editReviewRequest) {
        Users user = getUserService.find(username);
        Review review = reviewRepository.findById(editReviewRequest.reviewId())
                .orElseThrow(ReviewNotFound::new);

        review.checkUserIsItsReviewer(user.username());

        review.modify(editReviewRequest.rate(), editReviewRequest.comment());

        return review;
    }
}
