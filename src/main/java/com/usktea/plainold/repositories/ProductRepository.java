package com.usktea.plainold.repositories;

import com.usktea.plainold.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
