package com.usktea.plainold.specifications;

import com.usktea.plainold.models.answer.Answer;
import com.usktea.plainold.models.answer.Status;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AnswerSpecification {
    public static Specification<Answer> withInquiryIds(List<Long> inquiryIds, Sort sort) {
        return (root, query, criteriaBuilder) -> {
            if (inquiryIds == null || inquiryIds.isEmpty()) {
                return null;
            }

            if (sort != null) {
                query.orderBy(criteriaBuilder.asc(root.get("createdAt")));
            }

            return root.get("inquiryId").in(inquiryIds);
        };
    }

    public static Specification<Answer> withStatus(Status active) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), active);
    }
}
