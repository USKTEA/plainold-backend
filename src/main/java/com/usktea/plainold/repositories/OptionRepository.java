package com.usktea.plainold.repositories;

import com.usktea.plainold.models.option.Option;
import com.usktea.plainold.models.product.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    Optional<Option> findByProductId(ProductId id);
}
