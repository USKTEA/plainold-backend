package com.usktea.plainold.specifications;

import com.usktea.plainold.models.like.Likes;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import org.springframework.data.jpa.domain.Specification;

public class LikeSpecification {
    public static Specification<Likes> withUsername(Username username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("username"), username);
    }

    public static Specification<Likes> withProductId(ProductId productId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("productId"), productId);
    }
}
