package com.usktea.plainold.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ItemDto {
    private Long productId;
    private Long price;
    private String name;
    private String thumbnailUrl;
    private Long shippingFee;
    private Long freeShippingAmount;
    private Long quantity;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OptionDto option;

    public ItemDto() {
    }

    public ItemDto(
            Long productId,
            Long price,
            String productName,
            String thumbnailUrl,
            Long shippingFee,
            Long freeShippingAmount,
            Long quantity,
            OptionDto option) {
        this.productId = productId;
        this.price = price;
        this.name = productName;
        this.thumbnailUrl = thumbnailUrl;
        this.shippingFee = shippingFee;
        this.freeShippingAmount = freeShippingAmount;
        this.quantity = quantity;
        this.option = option;
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

    public Long getFreeShippingAmount() {
        return freeShippingAmount;
    }

    public Long getQuantity() {
        return quantity;
    }

    public OptionDto getOption() {
        return option;
    }
}
