package com.usktea.plainold.dtos;

import com.usktea.plainold.models.answer.Content;

public class EditAnswerRequest {
    private Long id;
    private Content content;

    public EditAnswerRequest(Long id, Content content) {
        this.id = id;
        this.content = content;
    }

    public static EditAnswerRequest of(EditAnswerRequestDto editAnswerRequestDto) {
        return new EditAnswerRequest(
                editAnswerRequestDto.getId(),
                new Content(editAnswerRequestDto.getContent())
        );
    }

    public static EditAnswerRequest fake(Long answerId) {
        return new EditAnswerRequest(
                answerId,
                new Content("이렇게 수정된 답변")
        );
    }

    public Long id() {
        return id;
    }

    public Content content() {
        return content;
    }
}
