package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.ProductDetail;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.option.Option;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.repositories.OptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class GetProductDetailServiceTest {
    private GetProductService getProductService;
    private GetProductDetailService getProductDetailService;
    private OptionRepository optionRepository;

    @BeforeEach
    void setup() {
        getProductService = mock(GetProductService.class);
        optionRepository = mock(OptionRepository.class);
        getProductDetailService = new GetProductDetailService(getProductService, optionRepository);
    }

    @Test
    void whenProductExits() {
        ProductId id = new ProductId(1L);

        given(getProductService.find(id))
                .willReturn(Product.fake(id));

        given(optionRepository.findByProductId(id))
                .willReturn(Optional.of(Option.fake(id)));

        ProductDetail productDetail = getProductDetailService.detail(id);

        assertThat(productDetail).isEqualTo(ProductDetail.fake(id));
    }

    @Test
    void whenProductNotExists() {
        ProductId id = new ProductId(9_999_999L);

        given(getProductService.find(id))
                .willThrow(ProductNotFound.class);

        assertThrows(ProductNotFound.class,
                () -> getProductDetailService.detail(id));
    }

    @Test
    void whenProductOptionExists() {
        ProductId id = new ProductId(1L);

        given(getProductService.find(id))
                .willReturn(Product.fake(id));

        given(optionRepository.findByProductId(id))
                .willReturn(Optional.of(Option.fake(id)));

        ProductDetail productDetail = getProductDetailService.detail(id);

        assertThat(productDetail.getOptionData()).isNotNull();
    }

    @Test
    void whenProductOptionNotExists() {
        ProductId id = new ProductId(1L);

        given(getProductService.find(id))
                .willReturn(Product.fake(id));

        given(optionRepository.findByProductId(id))
                .willReturn(Optional.empty());

        ProductDetail productDetail = getProductDetailService.detail(id);

        assertThat(productDetail.getOptionData()).isNull();
    }
}
