package com.usktea.plainold.repositories;

import com.usktea.plainold.models.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
