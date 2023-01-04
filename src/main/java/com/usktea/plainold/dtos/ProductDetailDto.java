package com.usktea.plainold.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.usktea.plainold.models.category.CategoryId;
import com.usktea.plainold.models.common.Money;
import com.usktea.plainold.models.option.OptionData;
import com.usktea.plainold.models.product.Description;
import com.usktea.plainold.models.product.Image;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.product.ProductName;
import com.usktea.plainold.models.product.ProductStatus;
import com.usktea.plainold.models.product.Shipping;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OptionDataDto optionData;

    public ProductDetailDto() {
    }

    public ProductDetailDto(ProductId id,
                            Money price,
                            ProductName productName,
                            CategoryId categoryId,
                            Image image,
                            Description description,
                            Shipping shipping,
                            ProductStatus status,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt,
                            OptionData optionData
    ) {
        this.id = id.value();
        this.price = price.getAmount();
        this.name = productName.getValue();
        this.categoryId = categoryId.value();
        this.image = image.toDto();
        this.description = description.toDto();
        this.shipping = shipping.toDto();
        this.status = status.value();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        setOptionData(optionData);
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

    public OptionDataDto getOptionData() {
        return optionData;
    }

    private void setOptionData(OptionData optionData) {
        if (optionData == null) {
            return;
        }

        this.optionData = optionData.toDto();
    }
}
