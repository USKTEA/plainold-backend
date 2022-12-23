package com.usktea.plainold.dtos;

public class ImageDto {
    private String thumbnailUrl;
    private String[] productImageUrls;

    public ImageDto() {
    }

    public ImageDto(String thumbnailUrl, String[] productImageUrls) {
        this.thumbnailUrl = thumbnailUrl;
        this.productImageUrls = productImageUrls;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String[] getProductImageUrls() {
        return productImageUrls;
    }
}
