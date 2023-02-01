package com.usktea.plainold.dtos;

import com.usktea.plainold.models.inquiry.Content;
import com.usktea.plainold.models.inquiry.Title;

public class EditInquiryRequest {
    private Long id;
    private Title title;
    private Content content;

    public EditInquiryRequest(Long id, Title title, Content content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static EditInquiryRequest of(EditInquiryRequestDto editInquiryRequestDto) {
        return new EditInquiryRequest(
                editInquiryRequestDto.getId(),
                new Title(editInquiryRequestDto.getTitle()),
                new Content(editInquiryRequestDto.getContent())
        );
    }

    public static EditInquiryRequest fake(Long id) {
        return new EditInquiryRequest(
                id,
                new Title("제목 수정"),
                new Content("내용 수정")
        );
    }

    public Long id() {
        return id;
    }

    public Title title() {
        return title;
    }

    public Content content() {
        return content;
    }
}
