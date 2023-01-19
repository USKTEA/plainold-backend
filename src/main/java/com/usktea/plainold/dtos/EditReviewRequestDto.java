package com.usktea.plainold.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditReviewRequestDto {
    @NotNull
    private Long id;

    @NotNull
    private Integer rate;

    @NotBlank
    private String comment;

    public EditReviewRequestDto() {
    }

    public Long getId() {
        return id;
    }

    public Integer getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }
}
