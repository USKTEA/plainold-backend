package com.usktea.plainold.specifications;

import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderStatus;
import com.usktea.plainold.models.user.Username;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {
    public static Specification<Order> equal(
            Username username,
            OrderStatus status
    ) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.equal(root.get("username"), username));
                predicates.add(criteriaBuilder.equal(root.get("status"), status));

                return criteriaBuilder.and(predicates.toArray((new Predicate[predicates.size()])));
            }
        };
    }
}
