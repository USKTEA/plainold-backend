package com.usktea.plainold.specifications;

import com.usktea.plainold.models.category.CategoryId;
import com.usktea.plainold.models.product.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> equalCategoryId(
            CategoryId categoryId
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoryId"), categoryId);
    }
}
