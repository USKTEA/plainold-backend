package com.usktea.plainold.dtos;

import com.sun.istack.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateReplyRequestDto {
    @NotNull
    private Long reviewId;

    @Nullable
    private Long parent;

    @NotBlank
    private String comment;

    public CreateReplyRequestDto() {
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
}
