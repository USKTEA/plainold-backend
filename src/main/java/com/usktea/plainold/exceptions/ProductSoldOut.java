package com.usktea.plainold.exceptions;

public class ProductSoldOut extends RuntimeException {
    public ProductSoldOut() {
        super("상품 재고가 없습니다.");
    }
}
