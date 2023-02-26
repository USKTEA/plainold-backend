package com.usktea.plainold.applications.like;

import com.usktea.plainold.applications.product.GetProductService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.UserAlreadyLikedProduct;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.like.Likes;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class CreateLikeServiceTest {
    private GetUserLikesService getUserLikesService;
    private LikeRepository likeRepository;
    private CreateLikeService createLikeService;

    @BeforeEach
    void setup() {
        getUserLikesService = mock(GetUserLikesService.class);

        likeRepository = mock(LikeRepository.class);
        createLikeService = new CreateLikeService(getUserLikesService, likeRepository);
    }

    @Test
    void whenUserNotExist() {
        Username username = new Username("notExists@gmail.com");
        ProductId productId = new ProductId(1L);

        given(getUserLikesService.getLikes(username, productId)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class, () -> createLikeService.create(username, productId));
    }

    @Test
    void whenProductNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(9_999_999L);

        given(getUserLikesService.getLikes(username, productId)).willThrow(ProductNotFound.class);

        assertThrows(ProductNotFound.class, () -> createLikeService.create(username, productId));
    }

    @Test
    void whenUserAlreadyLikedProduct() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);

        given(getUserLikesService.getLikes(username, productId)).willReturn(List.of(Likes.fake(username)));

        assertThrows(UserAlreadyLikedProduct.class, () -> createLikeService.create(username, productId));
    }

    @Test
    void whenCreateLikeSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);

        given(getUserLikesService.getLikes(username, productId)).willReturn(List.of());
        given(likeRepository.save(any(Likes.class))).willReturn(Likes.fake(username));

        Long id = createLikeService.create(username, productId);

        assertThat(id).isNotNull();

        verify(likeRepository).save(any(Likes.class));
    }
}
