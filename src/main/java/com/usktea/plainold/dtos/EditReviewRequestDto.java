package com.usktea.plainold.dtos;

import com.sun.istack.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditReviewRequestDto {
    @NotNull
    private Long id;

    @NotNull
    private Integer rate;

    @NotBlank
    private String comment;

    @Nullable
    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }
}
