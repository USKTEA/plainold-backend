package com.usktea.plainold.models;

import com.usktea.plainold.dtos.ProductDto;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private ProductName productName;

    @Embedded
    private Price price;

    @Embedded
    private CategoryId categoryId;

    @Embedded
    private ThumbnailUrl thumbnailUrl;

    public Product() {
    }

    public Product(Long id, ProductName productName, Price price, CategoryId categoryId, ThumbnailUrl thumbnailUrl) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.categoryId = categoryId;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static Product fake(Long id) {
        return new Product(
                id,
                new ProductName("T-shirt"),
                new Price(1_000L),
                new CategoryId(1L),
                new ThumbnailUrl("http://url.com"));
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
        return Objects.hash(id, thumbnailUrl, productName, categoryId, price);
    }

    public ProductDto toDto() {
        return new ProductDto(
                id,
                productName.value(),
                price.amount(),
                categoryId.value(),
                thumbnailUrl.value()
        );
    }
}

