package com.usktea.plainold.applications;

import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.CategoryId;
import com.usktea.plainold.models.Product;
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
class GetProductServiceTest {
    private GetProductService getProductService;
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        getProductService = new GetProductService(productRepository);
    }

    @Test
    void listWithoutCategory() {
        Page<Product> page = new PageImpl<>(List.of(Product.fake(1L)));

        given(productRepository.findAll(nullable(Specification.class), any(Pageable.class)))
                .willReturn(page);

        Page<Product> products = getProductService.list(new CategoryId(1L), 1);

        assertThat(products).hasSize(1);
    }

    @Test
    void listWithCategory() {
        Page<Product> page = new PageImpl<>(List.of(Product.fake(1L)));

        given(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(page);

        Page<Product> products = getProductService.list(new CategoryId(1L), 1);

        assertThat(products).hasSize(1);
    }

    @Test
    void whenProductExits() {
        Long id = 1L;

        given(productRepository.findById(id))
                .willReturn(Optional.of(Product.fake(id)));

        Product product = getProductService.product(id);

        assertThat(product).isEqualTo(Product.fake(id));
    }

    @Test
    void whenProductNotExists() {
        Long id = 9_999_999L;

        given(productRepository.findById(id))
                .willThrow(ProductNotFound.class);

        assertThrows(ProductNotFound.class, () -> getProductService.product(id));
    }
}
