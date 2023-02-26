package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.like.CountProductLikesService;
import com.usktea.plainold.applications.like.CreateLikeService;
import com.usktea.plainold.applications.like.DeleteLikeService;
import com.usktea.plainold.applications.like.GetUserLikesService;
import com.usktea.plainold.exceptions.LikeNotExists;
import com.usktea.plainold.exceptions.NotHaveDeleteLikeAuthority;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.UserAlreadyLikedProduct;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.like.Likes;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(LikeController.class)
class LikesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountProductLikesService countProductLikesService;

    @MockBean
    private GetUserLikesService getUserLikesService;

    @MockBean
    private CreateLikeService createLikeService;

    @MockBean
    private DeleteLikeService deleteLikeService;

    @SpyBean
    private JwtUtil jwtUtil;

    @Test
    void whenGetProductLikesSuccess() throws Exception {
        Long productId = 1L;

        given(countProductLikesService.counts(any(ProductId.class)))
                .willReturn(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/likes?productId=%s", productId)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"counts\"")
                ));
    }

    @Test
    void whenGetProductLikesFailed() throws Exception {
        Long productId = 9_999_999L;

        given(countProductLikesService.counts(any(ProductId.class)))
                .willThrow(ProductNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/likes?productId=%s", productId)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetLikesByUserWithProductSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long productId = 1L;

        given(getUserLikesService.getLikes(any(Username.class), any(ProductId.class)))
                .willReturn(List.of(Likes.fake(username)));

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/likes/me?productId=%s", productId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"likes\"")
                ));
    }

    @Test
    void whenGetLikesByUserSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        given(getUserLikesService.getLikes(any(Username.class)))
                .willReturn(List.of(Likes.fake(username)));

        mockMvc.perform(MockMvcRequestBuilders.get("/likes/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"likes\"")
                ));
    }

    @Test
    void whenLikesByUserNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        given(getUserLikesService.getLikes(any(Username.class)))
                .willReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/likes/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenLikesByUserWithProductNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long productId = 1L;

        given(getUserLikesService.getLikes(any(Username.class), any(ProductId.class)))
                .willReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/likes/me?productId=%s", productId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenGetLikesByUserAndUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long productId = 1L;

        given(getUserLikesService.getLikes(any(Username.class), any(ProductId.class)))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/likes/me?productId=%s", productId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenTryToGetNotExistsProductsUserLike() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long productId = 9_999_999L;

        given(getUserLikesService.getLikes(any(Username.class), any(ProductId.class)))
                .willThrow(ProductNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/likes/me?productId=%s", productId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateLikeSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long productId = 1L;

        given(createLikeService.create(username, new ProductId(productId)))
                .willReturn(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.post("/likes")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":" + productId + "" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }

    @Test
    void whenTryToCreateNotExistProductLike() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long productId = 9_999_999L;

        given(createLikeService.create(username, new ProductId(productId)))
                .willThrow(ProductNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/likes")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":" + productId + "" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateLikeUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long productId = 1L;

        given(createLikeService.create(username, new ProductId(productId)))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/likes")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":" + productId + "" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserAlreadyLikedProduct() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long productId = 1L;

        given(createLikeService.create(username, new ProductId(productId)))
                .willThrow(UserAlreadyLikedProduct.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/likes")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":" + productId + "" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDeleteLikeSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 1L;

        given(deleteLikeService.delete(username, id)).willReturn(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/likes/%d", id))
                        .header("Authorization", "Bearer" + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }

    @Test
    void whenTryToDeleteNotHisOwnLike() throws Exception {
        Username username = new Username("otherUser@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 1L;

        given(deleteLikeService.delete(any(Username.class), anyLong()))
                .willThrow(NotHaveDeleteLikeAuthority.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/likes/%d", id))
                        .header("Authorization", "Bearer" + token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenDeleteLikeUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 1L;

        given(deleteLikeService.delete(any(Username.class), anyLong()))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/likes/%d", id))
                        .header("Authorization", "Bearer" + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenTryToDeleteNotExistsLike() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 9_999_999L;

        given(deleteLikeService.delete(any(Username.class), anyLong()))
                .willThrow(LikeNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/likes/%d", id))
                        .header("Authorization", "Bearer" + token))
                .andExpect(status().isBadRequest());
    }
}
