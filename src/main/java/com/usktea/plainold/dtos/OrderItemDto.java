package com.usktea.plainold.dtos;

public class OrderItemDto {
    private Long id;
    private Long productId;
    private Long price;
    private String name;
    private String thumbnailUrl;
    private Long shippingFee;
    private Long quantity;
    private Long totalPrice;

    public OrderItemDto() {
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Long getShippingFee() {
        return shippingFee;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }
}
