package com.usktea.plainold.models.product;

import com.usktea.plainold.dtos.ProductDto;
import com.usktea.plainold.exceptions.InvalidProductPrice;
import com.usktea.plainold.exceptions.ProductSoldOut;
import com.usktea.plainold.models.category.CategoryId;
import com.usktea.plainold.models.common.Money;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Product {
    @Id
    @AttributeOverride(name = "productId", column = @Column(name = "id"))
    private ProductId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private ProductName productName;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "price"))
    private Money price;

    @Embedded
    private CategoryId categoryId;

    @Embedded
    private Image image;

    @Embedded
    private Description description;

    @Embedded
    private Shipping shipping;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Product() {
    }

    public Product(ProductId id, ProductName productName, Money price, CategoryId categoryId, Image image) {
        this.id = id;
        this.productName = productName;
        setPrice(price);
        this.categoryId = categoryId;
        this.image = image;
    }

    public Product(ProductId id,
                   Money price,
                   ProductName productName,
                   CategoryId categoryId,
                   Image image,
                   Description description,
                   Shipping shipping,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
        this.id = id;
        setPrice(price);
        this.productName = productName;
        this.categoryId = categoryId;
        this.image = image;
        this.description = description;
        this.shipping = shipping;
        this.status = ProductStatus.ON_SALE;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Product(ProductId id,
                   Money price,
                   ProductName productName,
                   CategoryId categoryId,
                   Image image,
                   Description description,
                   Shipping shipping,
                   ProductStatus status,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
        this.id = id;
        setPrice(price);
        this.productName = productName;
        this.categoryId = categoryId;
        this.image = image;
        this.description = description;
        this.shipping = shipping;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Product fake(Money price) {
        return new Product(
                new ProductId(1L),
                new ProductName("T-shirt"),
                price,
                new CategoryId(1L),
                Image.fake(new ThumbnailUrl("http://url.com"))
        );
    }

    public static Product fake(ProductId id) {
        return new Product(
                id,
                new Money(1_000L),
                new ProductName("T-shirt"),
                new CategoryId(1L),
                Image.fake(
                        new ThumbnailUrl("1"),
                        Set.of(new ProductImageUrl("1"))
                ),
                Description.fake("Good", "Very Good"),
                Shipping.fake(ShippingMethod.Parcel),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public static Product fake(ProductStatus productStatus) {
        return new Product(
                new ProductId(1L),
                new Money(1_000L),
                new ProductName("T-shirt"),
                new CategoryId(1L),
                Image.fake(
                        new ThumbnailUrl("1"),
                        Set.of(new ProductImageUrl("1"))
                ),
                Description.fake("Good", "Very Good"),
                Shipping.fake(ShippingMethod.Parcel),
                productStatus,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public void checkIsSoldOut() {
        if (status.equals(ProductStatus.SOLD_OUT)) {
            throw new ProductSoldOut();
        }
    }

    private void setPrice(Money price) {
        if (price.getAmount() < 0) {
            throw new InvalidProductPrice();
        }

        this.price = price;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Product otherProduct = (Product) other;

        return Objects.equals(this.id, otherProduct.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, productName, categoryId, price);
    }

    public ProductDto toDto() {
        return new ProductDto(
                id.value(),
                productName.getValue(),
                price.getAmount(),
                categoryId.value(),
                image.thumbnail()
        );
    }

    public ProductId id() {
        return id;
    }

    public Money price() {
        return price;
    }

    public ProductName name() {
        return productName;
    }

    public CategoryId categoryId() {
        return categoryId;
    }

    public Image image() {
        return image;
    }

    public Description description() {
        return description;
    }

    public Shipping shipping() {
        return shipping;
    }

    public ProductStatus status() {
        return status;
    }

    public LocalDateTime createAt() {
        return createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
