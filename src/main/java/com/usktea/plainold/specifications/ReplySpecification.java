package com.usktea.plainold.specifications;

import com.usktea.plainold.models.reply.Reply;
import com.usktea.plainold.models.reply.Status;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ReplySpecification {
    public static Specification<Reply> withReviewIds(List<Long> reviewIds, Sort sort) {
        return (root, query, criteriaBuilder) -> {
            if (reviewIds == null || reviewIds.isEmpty()) {
                return null;
            }

            if (sort != null) {
                query.orderBy(criteriaBuilder.asc(root.get("createdAt")));
            }

            return root.get("reviewId").in(reviewIds);
        };
    }

    public static Specification<Reply> withStatus(Status status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }
}
