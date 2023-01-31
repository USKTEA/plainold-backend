package com.usktea.plainold.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateInquiryRequestDto {
    @NotNull
    private Long productId;

    @NotBlank
    private String type;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public CreateInquiryRequestDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
