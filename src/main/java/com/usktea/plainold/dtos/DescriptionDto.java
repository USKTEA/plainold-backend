package com.usktea.plainold.dtos;

public class DescriptionDto {
    private String productSummary;
    private String productDetail;

    public DescriptionDto() {
    }

    public DescriptionDto(String productSummary, String productDetail) {
        this.productSummary = productSummary;
        this.productDetail = productDetail;
    }

    public String getProductSummary() {
        return productSummary;
    }

    public String getProductDetail() {
        return productDetail;
    }
}
