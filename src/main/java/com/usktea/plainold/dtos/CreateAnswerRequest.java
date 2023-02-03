package com.usktea.plainold.dtos;

import com.usktea.plainold.models.answer.Content;

public class CreateAnswerRequest {
    private Long inquiryId;
    private Content content;

    public CreateAnswerRequest(Long inquiryId, Content content) {
        this.inquiryId = inquiryId;
        this.content = content;
    }

    public static CreateAnswerRequest of(CreateAnswerRequestDto createAnswerRequestDto) {
        return new CreateAnswerRequest(
                createAnswerRequestDto.getInquiryId(),
                new Content(createAnswerRequestDto.getContent())
        );
    }

    public static CreateAnswerRequest fake(Long inquiryId) {
        return new CreateAnswerRequest(
                inquiryId,
                new Content("답변을 작성합니다")
        );
    }

    public Long inquiryId() {
        return inquiryId;
    }

    public Content content() {
        return content;
    }
}
