package com.usktea.plainold.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ReviewDto {
    private Long id;
    private Long productId;
    private ReviewerDto reviewer;
    private Integer rate;
    private String comment;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reviewImageUrl;
    private String createdAt;

    public ReviewDto() {
    }

    public ReviewDto(Long id,
                     Long productId,
                     ReviewerDto reviewer,
                     Integer rate,
                     String comment,
                     String reviewImageUrl,
                     String createdAt) {
        this.id = id;
        this.productId = productId;
        this.reviewer = reviewer;
        this.rate = rate;
        this.comment = comment;
        this.reviewImageUrl = reviewImageUrl;
        this.createdAt = createdAt;
    }

    public ReviewDto(Long id,
                     Long productId,
                     ReviewerDto reviewer,
                     Integer rate,
                     String comment,
                     String createdAt) {
        this.id = id;
        this.productId = productId;
        this.reviewer = reviewer;
        this.rate = rate;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public ReviewerDto getReviewer() {
        return reviewer;
    }

    public Integer getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }

    public String getReviewImageUrl() {
        return reviewImageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
