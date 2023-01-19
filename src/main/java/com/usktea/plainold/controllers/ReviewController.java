package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.CreateReviewService;
import com.usktea.plainold.applications.DeleteReviewService;
import com.usktea.plainold.applications.EditReviewService;
import com.usktea.plainold.applications.GetReviewsService;
import com.usktea.plainold.dtos.CreateReviewRequest;
import com.usktea.plainold.dtos.CreateReviewRequestDto;
import com.usktea.plainold.dtos.CreateReviewResultDto;
import com.usktea.plainold.dtos.DeleteReviewResultDto;
import com.usktea.plainold.dtos.EditReviewRequest;
import com.usktea.plainold.dtos.EditReviewRequestDto;
import com.usktea.plainold.dtos.EditReviewResultDto;
import com.usktea.plainold.dtos.PageDto;
import com.usktea.plainold.dtos.ReviewDto;
import com.usktea.plainold.dtos.ReviewsDto;
import com.usktea.plainold.exceptions.CreateReviewFailed;
import com.usktea.plainold.exceptions.DeleteReviewFailed;
import com.usktea.plainold.exceptions.EditReviewFailed;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.ReviewerNotMatch;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Username;
import org.springframework.data.domain.Page;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("reviews")
public class ReviewController {
    private final GetReviewsService getReviewsService;
    private final CreateReviewService createReviewService;
    private final EditReviewService editReviewService;
    private final DeleteReviewService deleteReviewService;

    public ReviewController(GetReviewsService getReviewsService,
                            CreateReviewService createReviewService,
                            EditReviewService editReviewService,
                            DeleteReviewService deleteReviewService) {
        this.getReviewsService = getReviewsService;
        this.createReviewService = createReviewService;
        this.editReviewService = editReviewService;
        this.deleteReviewService = deleteReviewService;
    }

    @GetMapping
    public ReviewsDto list(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber
    ) {
        Page<Review> reviews = getReviewsService.getReviews(new ProductId(productId), pageNumber);
        List<ReviewDto> reviewDtos = reviews
                .getContent()
                .stream()
                .map(Review::toDto)
                .collect(Collectors.toList());

        PageDto pageDto = new PageDto(pageNumber, reviews.getTotalPages());

        return new ReviewsDto(reviewDtos, pageDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateReviewResultDto create(
            @RequestAttribute Username username,
            @Valid @RequestBody CreateReviewRequestDto createReviewRequestDto
    ) {
        try {
            CreateReviewRequest createReviewRequest = CreateReviewRequest.of(createReviewRequestDto);

            Review review = createReviewService.create(username, createReviewRequest);

            return new CreateReviewResultDto(review.id());
        } catch (Exception exception) {
            throw new CreateReviewFailed(exception);
        }
    }

    @PatchMapping
    public EditReviewResultDto edit(
            @RequestAttribute Username username,
            @Valid @RequestBody EditReviewRequestDto editReviewRequestDto
    ) {
        try {
            EditReviewRequest editReviewRequest = EditReviewRequest.of(editReviewRequestDto);

            Review edited = editReviewService.edit(username, editReviewRequest);

            return new EditReviewResultDto(edited.id());
        } catch (ReviewerNotMatch reviewerNotMatch) {
            throw reviewerNotMatch;
        } catch (Exception exception) {
            throw new EditReviewFailed(exception.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public DeleteReviewResultDto delete(
            @RequestAttribute Username username,
            @PathVariable Long id) {
        try {
            Review deleted = deleteReviewService.delete(username, id);

            return new DeleteReviewResultDto(deleted.id());
        } catch (ReviewerNotMatch reviewerNotMatch) {
            throw reviewerNotMatch;
        } catch (Exception exception) {
            throw new DeleteReviewFailed(exception.getMessage());
        }
    }

    @ExceptionHandler(ReviewerNotMatch.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String reviewNotMatch(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(DeleteReviewFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String deleteReviewFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(EditReviewFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String editReviewFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CreateReviewFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String createReviewFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(ProductNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String productNotExists(Exception exception) {
        return exception.getMessage();
    }
}
