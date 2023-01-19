package com.usktea.plainold.dtos;

public class DeleteReviewResultDto {
    private Long reviewId;

    public DeleteReviewResultDto() {
    }

    public DeleteReviewResultDto(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getReviewId() {
        return reviewId;
    }
}
