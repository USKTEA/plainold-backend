package com.usktea.plainold.dtos;

public class CancelRequestDto {
    private Long id;
    private String orderNumber;
    private String content;
    private String createdAt;

    public CancelRequestDto() {
    }

    public CancelRequestDto(Long id, String orderNumber, String content, String createdAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
