package com.usktea.plainold.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditReplyRequestDto {
    @NotNull
    private Long id;

    @NotNull
    private Long reviewId;

    @NotBlank
    private String comment;

    public EditReplyRequestDto() {
    }

    public Long getId() {
        return id;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public String getComment() {
        return comment;
    }
}
