package com.usktea.plainold.dtos;

public class OrderItemDto {
    private Long id;
    private Long productId;
    private Long price;
    private String name;
    private String thumbnailUrl;
    private Long shippingFee;
    private Long freeShippingAmount;
    private Long quantity;
    private Long totalPrice;
    private OptionDto option;

    public OrderItemDto() {
    }

    public OrderItemDto(Long id,
                        Long productId,
                        Long price,
                        String name,
                        String thumbnailUrl,
                        Long shippingFee,
                        Long freeShippingAmount,
                        Long quantity,
                        Long totalPrice,
                        OptionDto option
    ) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.shippingFee = shippingFee;
        this.freeShippingAmount = freeShippingAmount;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.option = option;
    }

    public static OrderItemDto fake() {
        return new OrderItemDto(
                1L, 1L, 10_000L, "T-Shirt", "1", 2_500L, 50_000L, 1L, 10_000L,
                new OptionDto("XL", "Black")
        );
    }

    public static OrderItemDto fake(OptionDto optionDto) {
        return new OrderItemDto(
                1L, 1L, 10_000L, "T-Shirt", "1", 2_500L, 50_000L, 1L, 10_000L,
                optionDto
        );
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

    public OptionDto getOption() {
        return option;
    }

    public Long getFreeShippingAmount() {
        return freeShippingAmount;
    }
}
