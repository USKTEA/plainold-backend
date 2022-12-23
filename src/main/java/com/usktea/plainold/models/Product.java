package com.usktea.plainold.models;

import com.usktea.plainold.dtos.ProductDetailDto;
import com.usktea.plainold.dtos.ProductDto;
import com.usktea.plainold.exceptions.InvalidProductPrice;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
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

    private String status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Product() {
    }

    public Product(Long id, ProductName productName, Money price, CategoryId categoryId, Image image) {
        this.id = id;
        this.productName = productName;
        setPrice(price);
        this.categoryId = categoryId;
        this.image = image;
    }

    public Product(Long id,
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
        this.status = ProductStatus.ON_SALE.value();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Product fake(Money price) {
        return new Product(
                1L,
                new ProductName("T-shirt"),
                price,
                new CategoryId(1L),
                Image.fake(new ThumbnailUrl("http://url.com"))
        );
    }

    public static Product fake(Long id) {
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

    private void setPrice(Money price) {
        if (price.amount() < 0) {
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
                id,
                productName.value(),
                price.amount(),
                categoryId.value(),
                image.thumbnail()
        );
    }

    public Long id() {
        return id;
    }

    public ProductDetailDto toDetailDto() {
        return new ProductDetailDto(
                id,
                price,
                productName,
                categoryId,
                image,
                description,
                shipping,
                status,
                createdAt,
                updatedAt
        );
    }
}
