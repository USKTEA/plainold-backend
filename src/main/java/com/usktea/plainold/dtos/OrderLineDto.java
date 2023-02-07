package com.usktea.plainold.dtos;

public class OrderLineDto {
    private String productName;

    private String thumbnailUrl;

    private Long quantity;

    private Long totalPrice;

    private ItemOptionDto option;

    public OrderLineDto() {
    }

    public OrderLineDto(String productName,
                        String thumbnailUrl,
                        Long quantity,
                        Long totalPrice,
                        ItemOptionDto option) {
        this.productName = productName;
        this.thumbnailUrl = thumbnailUrl;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.option = option;
    }

    public String getProductName() {
        return productName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public ItemOptionDto getOption() {
        return option;
    }
}
