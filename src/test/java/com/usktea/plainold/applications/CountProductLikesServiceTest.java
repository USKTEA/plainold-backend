package com.usktea.plainold.applications;

import com.usktea.plainold.applications.like.CountProductLikesService;
import com.usktea.plainold.applications.product.GetProductService;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.like.Likes;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.repositories.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class CountProductLikesServiceTest {
    private GetProductService getProductService;
    private LikeRepository likeRepository;
    private CountProductLikesService countProductLikesService;

    @BeforeEach
    void setup() {
        likeRepository = mock(LikeRepository.class);
        getProductService = mock(GetProductService.class);
        countProductLikesService = new CountProductLikesService(getProductService, likeRepository);
    }

    @Test
    void whenProductNotExists() {
        ProductId productId = new ProductId(9_999_999L);

        given(getProductService.find(productId)).willThrow(ProductNotFound.class);

        assertThrows(ProductNotFound.class,
                () -> countProductLikesService.counts(productId));
    }

    @Test
    void whenProductExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(9_999_999L);

        given(getProductService.find(productId)).willReturn(Product.fake(productId));
        given(likeRepository.findAllByProductId(productId))
                .willReturn(List.of(Likes.fake(username)));

        Integer counts = countProductLikesService.counts(productId);

        assertThat(counts).isEqualTo(1);
    }
}
