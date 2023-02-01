package com.usktea.plainold.applications;

import com.usktea.plainold.applications.product.GetProductService;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class GetProductServiceTest {
    private GetProductService getProductService;
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        getProductService = new GetProductService(productRepository);
    }

    @Test
    void whenProductExists() {
        ProductId productId = new ProductId(1L);

        given(productRepository.findById(productId))
                .willReturn(Optional.of(Product.fake(productId)));

        Product product = getProductService.find(productId);

        assertThat(product.id()).isEqualTo(productId);
    }

    @Test
    void whenProductNotExists() {
        ProductId productId = new ProductId(9_999_999L);

        given(productRepository.findById(productId))
                .willReturn(Optional.empty());

        assertThrows(ProductNotFound.class, () -> getProductService.find(productId));
    }
}
