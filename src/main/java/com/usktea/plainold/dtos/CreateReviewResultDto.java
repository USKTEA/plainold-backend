package com.usktea.plainold.dtos;

public class CreateReviewResultDto {
    private Long reviewId;

    public CreateReviewResultDto() {
    }

    public CreateReviewResultDto(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getReviewId() {
        return reviewId;
    }
}
