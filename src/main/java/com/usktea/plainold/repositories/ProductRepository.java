package com.usktea.plainold.repositories;

import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, ProductId>, JpaSpecificationExecutor {
    Optional<Product> findById(ProductId id);
}
