package com.usktea.plainold.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditInquiryRequestDto {
    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public EditInquiryRequestDto() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
