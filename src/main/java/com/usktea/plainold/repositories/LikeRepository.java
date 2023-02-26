package com.usktea.plainold.repositories;

import com.usktea.plainold.models.like.Likes;
import com.usktea.plainold.models.product.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LikeRepository extends JpaRepository<Likes, Long>, JpaSpecificationExecutor {
    List<Likes> findAllByProductId(ProductId productId);
}
