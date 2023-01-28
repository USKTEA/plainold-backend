package com.usktea.plainold.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ReplyDto {
    private Long id;

    private Long reviewId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long parent;

    private String comment;

    private ReplierDto replier;

    private String createdAt;

    public ReplyDto() {
    }

    public ReplyDto(Long id,
                    Long reviewId,
                    Long parent,
                    String comment,
                    ReplierDto replier,
                    String createdAt) {
        this.id = id;
        this.reviewId = reviewId;
        this.parent = parent;
        this.comment = comment;
        this.replier = replier;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Long getParent() {
        return parent;
    }

    public String getComment() {
        return comment;
    }

    public ReplierDto getReplier() {
        return replier;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
