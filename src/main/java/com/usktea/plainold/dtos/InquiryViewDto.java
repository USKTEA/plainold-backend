package com.usktea.plainold.dtos;

public class InquiryViewDto {
    private Long id;
    private Long productId;
    private String status;
    private String type;
    private String title;
    private String content;
    private QueristDto querist;
    private String createdAt;

    public InquiryViewDto() {
    }

    public InquiryViewDto(Long id,
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

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getStatus() {
        return status;
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

    public QueristDto getQuerist() {
        return querist;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
