package com.usktea.plainold.models.product;

import com.usktea.plainold.dtos.ImageDto;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Embeddable
public class Image {
    private ThumbnailUrl thumbnailUrl;

    @ElementCollection
    private Set<ProductImageUrl> productImageUrls = new HashSet<>();

    public Image() {
    }

    public Image(ThumbnailUrl thumbnailUrl, Set<ProductImageUrl> productImageUrls) {
        this.thumbnailUrl = thumbnailUrl;
        this.productImageUrls = productImageUrls;
    }

    public static Image fake(ThumbnailUrl thumbnailUrl) {
        return new Image(thumbnailUrl, Set.of(new ProductImageUrl()));
    }

    public static Image fake(ThumbnailUrl thumbnailUrl, Set<ProductImageUrl> productImageUrls) {
        return new Image(thumbnailUrl, productImageUrls);
    }

    public String thumbnail() {
        return thumbnailUrl.value();
    }

    public Set<ProductImageUrl> productImageUrls() {
        return productImageUrls;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Image otherImage = (Image) other;

        return Objects.equals(thumbnailUrl, otherImage.thumbnailUrl)
                && Objects.equals(productImageUrls, otherImage.productImageUrls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thumbnailUrl, productImageUrls);
    }

    public ImageDto toDto() {
        String[] urls = productImageUrls.stream()
                .map(ProductImageUrl::productImageUrl)
                .toArray(String[]::new);

        return new ImageDto(thumbnailUrl.value(), urls);
    }
}
