package com.usktea.plainold.specifications;

import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.review.Review;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ReviewSpecification {
    public static Specification<Review> equal(List<OrderNumber> orderNumbers, ProductId productId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (orderNumbers != null) {
                predicates.add(root.get("orderNumber").in(orderNumbers));
            }

            if (productId != null) {
                predicates.add(criteriaBuilder.equal(root.get("productId"), productId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public static Specification<Review> equal(OrderNumber orderNumber, ProductId productId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (orderNumber != null) {
                predicates.add(criteriaBuilder.equal(root.get("orderNumber"), orderNumber));
            }

            if (productId != null) {
                predicates.add(criteriaBuilder.equal(root.get("productId"), productId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public static Specification<Review> equalProductId(ProductId productId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productId"), productId);
    }

    public static Specification<Review> imageUrlNotNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("imageUrl"));
    }
}
