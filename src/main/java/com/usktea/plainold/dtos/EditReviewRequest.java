package com.usktea.plainold.dtos;

import com.usktea.plainold.models.common.Comment;
import com.usktea.plainold.models.review.ImageUrl;
import com.usktea.plainold.models.review.Rate;

public class EditReviewRequest {
    private Long id;
    private Rate rate;
    private Comment comment;
    private ImageUrl imageUrl;

    public EditReviewRequest(Long id, Rate rate, Comment comment, ImageUrl imageUrl) {
        this.id = id;
        this.rate = rate;
        this.comment = comment;
        this.imageUrl = imageUrl;
    }

    public static EditReviewRequest of(EditReviewRequestDto editReviewRequestDto) {
        return new EditReviewRequest(
                editReviewRequestDto.getId(),
                new Rate(editReviewRequestDto.getRate()),
                new Comment(editReviewRequestDto.getComment()),
                new ImageUrl(editReviewRequestDto.getImageUrl())
        );
    }

    public static EditReviewRequest fake(Long reviewId) {
        return new EditReviewRequest(
                reviewId,
                new Rate(5),
                new Comment("아주 좋은 상품"),
                new ImageUrl("1")
        );
    }

    public Comment comment() {
        return comment;
    }

    public Rate rate() {
        return rate;
    }

    public Long reviewId() {
        return id;
    }

    public ImageUrl imageUrl() {
        return imageUrl;
    }
}
