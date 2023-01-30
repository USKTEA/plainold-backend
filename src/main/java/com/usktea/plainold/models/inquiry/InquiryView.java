package com.usktea.plainold.models.inquiry;

import com.usktea.plainold.dtos.InquiryViewDto;
import com.usktea.plainold.dtos.QueristDto;

public class InquiryView {
    private Long id;
    private Long productId;
    private String status;
    private String type;
    private String title;
    private String content;
    private QueristDto querist;
    private String createdAt;

    public InquiryView(Long id,
                       Long productId,
                       String status,
                       String type,
                       String title,
                       String content,
                       QueristDto querist,
                       String createdAt) {
        this.id = id;
        this.productId = productId;
        this.status = status;
        this.type = type;
        this.title = title;
        this.content = content;
        this.querist = querist;
        this.createdAt = createdAt;
    }

    public static InquiryView fake(Long productId) {
        return new InquiryView(
                1L,
                productId,
                "PENDING",
                "PUBLIC",
                "사이지 문의",
                "이렇게 입어도 될까요",
                new QueristDto("tjrxo1234@gmail.com", "김뚜루"),
                "2023-01-30 17:02"
        );
    }

    public String title() {
        return title;
    }

    public String content() {
        return content;
    }

    public InquiryViewDto toDto() {
        return new InquiryViewDto(
                id,
                productId,
                status,
                type,
                title,
                content,
                querist,
                createdAt
        );
    }

    public String createdAt() {
        return createdAt;
    }
}
