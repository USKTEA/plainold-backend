package com.usktea.plainold.specifications;

import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderStatus;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {
    public static Specification<Order> equal(
            Username username,
            OrderStatus status
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("username"), username));
            predicates.add(criteriaBuilder.equal(root.get("status"), status));

            return criteriaBuilder.and(predicates.toArray((new Predicate[predicates.size()])));
        };
    }

    public static Specification<Order> equal(ProductId productId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("orderLines").get("productId"), productId));
    }
}
