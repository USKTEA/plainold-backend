package com.usktea.plainold.dtos;

public class LikeDto {
    private Long id;
    private String username;
    private Long productId;
    private String createdAt;

    public LikeDto() {
    }

    public LikeDto(Long id,
                   String username,
                   Long productId,
                   String createdAt) {
        this.id = id;
        this.username = username;
        this.productId = productId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getProductId() {
        return productId;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
