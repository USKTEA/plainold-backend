package com.usktea.plainold.applications.review;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.ReviewNotFound;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class DeleteReviewService {
    private final GetUserService getUserService;
    private final ReviewRepository reviewRepository;

    public DeleteReviewService(GetUserService getUserService, ReviewRepository reviewRepository) {
        this.getUserService = getUserService;
        this.reviewRepository = reviewRepository;
    }

    public Review delete(Username username, Long id) {
        Users user = getUserService.find(username);

        Review review = reviewRepository.findById(id)
                .orElseThrow(ReviewNotFound::new);

        review.checkUserAuthority(user.username(), user.role());

        reviewRepository.delete(review);

        return review;
    }
}
