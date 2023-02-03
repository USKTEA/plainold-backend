package com.usktea.plainold.specifications;

import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.inquiry.Status;
import com.usktea.plainold.models.product.ProductId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class InquirySpecification {
    public static Specification<Inquiry> equalProductId(ProductId productId, Sort sort) {
        return (root, query, criteriaBuilder) -> {
            if (productId == null) {
                return null;
            }

            if (sort != null) {
                query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            }

            return criteriaBuilder.equal(root.get("productId"), productId);
        };
    }

    public static Specification<Inquiry> notDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("status"), Status.DELETED);
    }
}
