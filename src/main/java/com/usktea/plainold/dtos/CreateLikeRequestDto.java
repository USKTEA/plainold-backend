package com.usktea.plainold.dtos;

import javax.validation.constraints.NotNull;

public class CreateLikeRequestDto {
    @NotNull
    private Long productId;

    public CreateLikeRequestDto() {
    }

    public Long getProductId() {
        return productId;
    }
}
