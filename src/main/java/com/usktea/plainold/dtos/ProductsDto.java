package com.usktea.plainold.dtos;

import java.util.List;

public class ProductsDto {
    private List<ProductDto> products;

    public ProductsDto() {
    }

    public ProductsDto(List<ProductDto> products) {
        this.products = products;
    }

    public List<ProductDto> getProducts() {
        return products;
    }
}
