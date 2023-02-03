package com.usktea.plainold.dtos;

public class AnswerDto {
    private Long id;
    private Long inquiryId;
    private AnswererDto answerer;
    private String content;
    private String createdAt;

    public AnswerDto() {
    }

    public AnswerDto(Long id,
                     Long inquiryId,
                     AnswererDto answerer,
                     String content,
                     String createdAt) {
        this.id = id;
        this.inquiryId = inquiryId;
        this.answerer = answerer;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getInquiryId() {
        return inquiryId;
    }

    public AnswererDto getAnswerer() {
        return answerer;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
