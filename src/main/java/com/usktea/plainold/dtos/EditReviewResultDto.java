package com.usktea.plainold.dtos;

public class EditReviewResultDto {
    private Long reviewId;

    public EditReviewResultDto() {
    }

    public EditReviewResultDto(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getReviewId() {
        return reviewId;
    }
}
