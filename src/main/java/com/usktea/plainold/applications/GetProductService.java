package com.usktea.plainold.applications;

import com.usktea.plainold.exceptions.CategoryNotFound;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.Category;
import com.usktea.plainold.models.CategoryId;
import com.usktea.plainold.models.Product;
import com.usktea.plainold.repositories.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public GetProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<Product> list(CategoryId categoryId, Integer page) {
        Specification<Product> specification = ((root, query, criteriaBuilder) -> null);

        if (!categoryId.isNull()) {
            Category category = categoryRepository.findById(categoryId.value())
                    .orElseThrow(CategoryNotFound::new);

            specification = Specification.where(ProductSpecification.equalCategoryId(
                    new CategoryId(category.getId())));
        }

        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page - 1, 8, sort);

        Page<Product> products = productRepository.findAll(specification, pageable);

        return products;
    }

    public Product product(Long id) {
        Product fake = Product.fake(id);

        productRepository.save(fake);

        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFound::new);

        return product;
    }
}
