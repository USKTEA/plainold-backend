package com.usktea.plainold.builders;

import com.usktea.plainold.models.option.OptionData;
import com.usktea.plainold.dtos.ProductDetail;
import com.usktea.plainold.models.category.CategoryId;
import com.usktea.plainold.models.common.Money;
import com.usktea.plainold.models.product.Description;
import com.usktea.plainold.models.product.Image;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.product.ProductName;
import com.usktea.plainold.models.product.ProductStatus;
import com.usktea.plainold.models.product.Shipping;

import java.time.LocalDateTime;

public class ProductDetailBuilder implements Builder {
    private ProductId productId;
    private Money price;
    private ProductName productName;
    private CategoryId categoryId;
    private Image image;
    private Description description;
    private Shipping shipping;
    private ProductStatus status;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private OptionData optionData;

    @Override
    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    @Override
    public void setPrice(Money price) {
        this.price = price;
    }

    @Override
    public void setProductName(ProductName productName) {
        this.productName = productName;
    }

    @Override
    public void setCategoryId(CategoryId categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    @Override
    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    @Override
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public void setOptionData(OptionData optionData) {
        this.optionData = optionData;
    }

    public ProductDetail getResult() {
        return new ProductDetail(
                productId,
                price,
                productName,
                categoryId,
                image,
                description,
                shipping,
                status,
                createAt,
                updatedAt,
                optionData);
    }
}
