package com.usktea.plainold.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateAnswerRequestDto {
    @NotNull
    private Long inquiryId;

    @NotBlank
    private String content;

    public CreateAnswerRequestDto() {
    }

    public Long getInquiryId() {
        return inquiryId;
    }

    public String getContent() {
        return content;
    }
}
