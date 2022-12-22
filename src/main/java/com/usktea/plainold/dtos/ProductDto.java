package com.usktea.plainold.dtos;

public class ProductDto {
    private Long id;
    private String name;
    private Long price;
    private Long categoryId;
    private String thumbnailUrl;

    public ProductDto() {
    }

    public ProductDto(Long id, String name, Long price, Long categoryId, String thumbnailUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
