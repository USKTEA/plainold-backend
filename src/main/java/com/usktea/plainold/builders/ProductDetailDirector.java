package com.usktea.plainold.builders;

import com.usktea.plainold.models.option.OptionData;
import com.usktea.plainold.models.option.Option;
import com.usktea.plainold.models.product.Product;

public class ProductDetailDirector {
    public void productDetailWithOption(Builder builder, Product product, Option option) {
        builder.setProductId(product.id());
        builder.setPrice(product.price());
        builder.setProductName(product.name());
        builder.setCategoryId(product.categoryId());
        builder.setImage(product.image());
        builder.setDescription(product.description());
        builder.setShipping(product.shipping());
        builder.setStatus(product.status());
        builder.setCreateAt(product.createAt());
        builder.setUpdatedAt(product.updatedAt());
        builder.setOptionData(new OptionData(option.getSizes(), option.getColors()));
    }

    public void productDetailWithoutOption(Builder builder, Product product) {
        builder.setProductId(product.id());
        builder.setPrice(product.price());
        builder.setProductName(product.name());
        builder.setCategoryId(product.categoryId());
        builder.setImage(product.image());
        builder.setDescription(product.description());
        builder.setShipping(product.shipping());
        builder.setStatus(product.status());
        builder.setCreateAt(product.createAt());
        builder.setUpdatedAt(product.updatedAt());
    }
}
