package com.usktea.plainold.dtos;

import com.usktea.plainold.models.category.CategoryId;
import com.usktea.plainold.models.common.Money;
import com.usktea.plainold.models.option.OptionData;
import com.usktea.plainold.models.product.Description;
import com.usktea.plainold.models.product.Image;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.product.ProductImageUrl;
import com.usktea.plainold.models.product.ProductName;
import com.usktea.plainold.models.product.ProductStatus;
import com.usktea.plainold.models.product.Shipping;
import com.usktea.plainold.models.product.ShippingMethod;
import com.usktea.plainold.models.product.ThumbnailUrl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class ProductDetail {
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

    public ProductDetail(ProductId productId,
                         Money price,
                         ProductName productName,
                         CategoryId categoryId,
                         Image image,
                         Description description,
                         Shipping shipping,
                         ProductStatus status,
                         LocalDateTime createAt,
                         LocalDateTime updatedAt,
                         OptionData optionData) {
        this.productId = productId;
        this.price = price;
        this.productName = productName;
        this.categoryId = categoryId;
        this.image = image;
        this.description = description;
        this.shipping = shipping;
        this.status = status;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
        this.optionData = optionData;
    }

    public static ProductDetail fake(ProductId productId) {
        Money price = new Money(1_000L);
        ProductName productName = new ProductName("T-Shirt");
        CategoryId categoryId = new CategoryId(1L);
        Image image = Image.fake(
                new ThumbnailUrl("1"),
                Set.of(new ProductImageUrl("1"))
        );
        Description description = Description.fake("Good", "Very Good");
        Shipping shipping = Shipping.fake(ShippingMethod.Parcel);
        ProductStatus status = ProductStatus.ON_SALE;
        LocalDateTime createAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        OptionData optionData = OptionData.fake();

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
                optionData
        );
    }

    public ProductDetailDto toDto() {
        return new ProductDetailDto(
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

    public OptionData getOptionData() {
        return optionData;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        ProductDetail otherProductDetail = (ProductDetail) object;

        return Objects.equals(productId, otherProductDetail.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    public Long productId() {
        return productId.getProductId();
    }
}
