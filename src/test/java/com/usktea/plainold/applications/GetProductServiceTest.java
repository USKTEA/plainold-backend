package com.usktea.plainold.applications;

import com.usktea.plainold.models.Product;
import com.usktea.plainold.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    void list() {
        given(productRepository.findAll())
                .willReturn(List.of(Product.fake(1L)));

        List<Product> products = getProductService.list();

        assertThat(products).hasSize(1);
    }
}
