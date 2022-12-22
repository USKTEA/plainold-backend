package com.usktea.plainold.dtos;

import java.util.List;

public class ProductsDto {
    private List<ProductDto> products;
    private PageDto page;

    public ProductsDto() {
    }

    public ProductsDto(List<ProductDto> products) {
        this.products = products;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public PageDto getPage() {
        return page;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }
}
