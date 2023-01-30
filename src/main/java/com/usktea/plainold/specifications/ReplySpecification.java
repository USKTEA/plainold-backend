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
        return new Specification<Reply>() {
            @Override
            public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (reviewIds == null || reviewIds.isEmpty()) {
                    return null;
                }

                if (sort != null) {
                    query.orderBy(criteriaBuilder.asc(root.get("createdAt")));
                }

                return root.get("reviewId").in(reviewIds);
            }
        };
    }

    public static Specification<Reply> withStatus(Status status) {
        return new Specification<Reply>() {
            @Override
            public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("status"), status);
            }
        };
    }
}
