package com.usktea.plainold.applications.like;

import com.usktea.plainold.applications.product.GetProductService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.like.Likes;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class GetUserLikesServiceTest {
    private GetUserService getUserService;
    private GetProductService getProductService;
    private LikeRepository likeRepository;
    private GetUserLikesService getUserLikesService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        getProductService = mock(GetProductService.class);
        likeRepository = mock(LikeRepository.class);
        getUserLikesService = new GetUserLikesService(
                getUserService, getProductService, likeRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");
        ProductId productId = new ProductId(1L);

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class,
                () -> getUserLikesService.getLikes(username, productId));
    }

    @Test
    void whenProductNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(9_999_999L);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getProductService.find(productId)).willThrow(ProductNotFound.class);

        assertThrows(ProductNotFound.class,
                () -> getUserLikesService.getLikes(username, productId));
    }

    @Test
    void whenGetLikesWithProductSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getProductService.find(productId)).willReturn(Product.fake(productId));
        given(likeRepository.findAll(any(Specification.class)))
                .willReturn(List.of(Likes.fake(username)));

        List<Likes> likes = getUserLikesService.getLikes(username, productId);

        assertThat(likes).isNotEmpty();
    }
}
