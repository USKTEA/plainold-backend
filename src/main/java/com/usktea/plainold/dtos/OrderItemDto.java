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
    private OptionDto option;

    public OrderItemDto() {
    }

    public OrderItemDto(long id,
                        long productId,
                        long price,
                        String name,
                        String thumbnailUrl,
                        long shippingFee,
                        long quantity,
                        long totalPrice,
                        OptionDto option
    ) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.shippingFee = shippingFee;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.option = option;
    }

    public static OrderItemDto fake() {
        return new OrderItemDto(
                1L, 1L, 10_000L, "T-Shirt", "1", 2_500L, 1L, 12_500L,
                new OptionDto("XL", "Black")
        );
    }

    public static OrderItemDto fake(OptionDto optionDto) {
        return new OrderItemDto(
                1L, 1L, 10_000L, "T-Shirt", "1", 2_500L, 1L, 12_500L,
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
}
