package com.usktea.plainold.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditAnswerRequestDto {
    @NotNull
    private Long id;

    @NotBlank
    private String content;

    public EditAnswerRequestDto() {
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
