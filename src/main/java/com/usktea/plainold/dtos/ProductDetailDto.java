package com.usktea.plainold.dtos;

import com.usktea.plainold.models.CategoryId;
import com.usktea.plainold.models.Description;
import com.usktea.plainold.models.Image;
import com.usktea.plainold.models.Money;
import com.usktea.plainold.models.ProductName;
import com.usktea.plainold.models.Shipping;

import java.time.LocalDateTime;

public class ProductDetailDto {
    private Long id;
    private Long price;
    private String name;
    private Long categoryId;
    private ImageDto image;
    private DescriptionDto description;
    private ShippingDto shipping;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductDetailDto() {
    }

    public ProductDetailDto(Long id,
                            Money price,
                            ProductName productName,
                            CategoryId categoryId,
                            Image image,
                            Description description,
                            Shipping shipping,
                            String status,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt) {
        this.id = id;
        this.price = price.amount();
        this.name = productName.value();
        this.categoryId = categoryId.value();
        this.image = image.toDto();
        this.description = description.toDto();
        this.shipping = shipping.toDto();
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public ImageDto getImage() {
        return image;
    }

    public DescriptionDto getDescription() {
        return description;
    }

    public ShippingDto getShipping() {
        return shipping;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
