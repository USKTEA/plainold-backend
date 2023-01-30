package com.usktea.plainold.dtos;

import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;

public class GetInquiriesRequestDto {
    private Username username;
    private ProductId productId;
    private Integer pageNumber;

    public GetInquiriesRequestDto(Username username, ProductId productId, Integer pageNumber) {
        this.username = username;
        this.productId = productId;
        this.pageNumber = pageNumber;
    }

    public Username username() {
        return username;
    }

    public ProductId productId() {
        return productId;
    }

    public Integer pageNumber() {
        return pageNumber;
    }
}
