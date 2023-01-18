package com.usktea.plainold.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateReviewRequestDto {
    @NotBlank
    private String orderNumber;

    @NotNull
    private Long productId;

    @NotNull
    private Integer rate;

    @NotBlank
    private String comment;

    public CreateReviewRequestDto() {
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }
}
