package com.usktea.plainold.applications;

import com.usktea.plainold.applications.product.GetProductsService;
import com.usktea.plainold.exceptions.CategoryNotFound;
import com.usktea.plainold.models.category.Category;
import com.usktea.plainold.models.category.CategoryId;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.repositories.CategoryRepository;
import com.usktea.plainold.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
@SuppressWarnings("unchecked")
class GetProductsServiceTest {
    private GetProductsService getProductsService;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        getProductsService = new GetProductsService(productRepository, categoryRepository);
    }

    @Test
    void listWithoutCategory() {
        ProductId productId = new ProductId(1L);

        Page<Product> page = new PageImpl<>(List.of(Product.fake(productId)));

        given(productRepository.findAll(nullable(Specification.class), any(Pageable.class)))
                .willReturn(page);

        Page<Product> products = getProductsService.list(new CategoryId(null), 1);

        assertThat(products).hasSize(1);
    }

    @Test
    void listWithCategory() {
        ProductId productId = new ProductId(1L);

        Page<Product> page = new PageImpl<>(List.of(Product.fake(productId)));

        given(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(page);

        given(categoryRepository.findById(1L))
                .willReturn(Optional.of(Category.fake()));

        Page<Product> products = getProductsService.list(new CategoryId(1L), 1);

        assertThat(products).hasSize(1);
    }

    @Test
    void whenCategoryNotExists() {
        given(categoryRepository.findById(9_999_999L))
                .willThrow(CategoryNotFound.class);

        assertThrows(CategoryNotFound.class,
                () -> getProductsService.list(new CategoryId(9_999_999L), 1));
    }
}
