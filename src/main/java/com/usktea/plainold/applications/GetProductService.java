package com.usktea.plainold.applications;

import com.usktea.plainold.models.CategoryId;
import com.usktea.plainold.models.Product;
import com.usktea.plainold.repositories.ProductRepository;
import com.usktea.plainold.specifications.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class GetProductService {
    private final ProductRepository productRepository;

    public GetProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> list(CategoryId categoryId, Integer page) {
        Specification<Product> specification = ((root, query, criteriaBuilder) -> null);

        if (!categoryId.isNull()) {
            specification = Specification.where(ProductSpecification.equalCategoryId(categoryId));
        }

        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page - 1, 8, sort);

        Page<Product> products = productRepository.findAll(specification, pageable);

        return products;
    }
}
