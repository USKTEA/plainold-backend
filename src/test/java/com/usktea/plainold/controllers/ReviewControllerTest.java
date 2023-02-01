package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.review.CreateReviewService;
import com.usktea.plainold.applications.review.DeleteReviewService;
import com.usktea.plainold.applications.review.EditReviewService;
import com.usktea.plainold.applications.review.GetReviewsService;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.ReviewNotFound;
import com.usktea.plainold.exceptions.ReviewerNotMatch;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
@ActiveProfiles("test")
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetReviewsService getReviewsService;

    @MockBean
    private CreateReviewService createReviewService;

    @MockBean
    private EditReviewService editReviewService;

    @MockBean
    private DeleteReviewService deleteReviewService;

    @SpyBean
    private JwtUtil jwtUtil;

    @Test
    void getReviewsSuccess() throws Exception {
        ProductId productId = new ProductId(1L);
        Boolean photoReviews = false;
        Integer pageNumber = 1;

        Page<Review> page = new PageImpl<>(List.of(Review.fake(productId)));

        given(getReviewsService.getReviews(productId, photoReviews, pageNumber))
                .willReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get(
                String.format("/reviews?productId=%d&photoReviews=%s&page=%d", productId.value(), photoReviews, pageNumber)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"reviews\"")
                ));
    }

    @Test
    void getReviewsFail() throws Exception {
        ProductId productId = new ProductId(1L);
        Integer pageNumber = 1;
        Boolean photoReviews = true;

        given(getReviewsService.getReviews(productId, photoReviews, pageNumber))
                .willThrow(ProductNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.get(
                String.format("/reviews?productId=%d&photoReviews=%s&page=%d", productId.value(), photoReviews, pageNumber)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createReviewSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);

        String token = jwtUtil.encode(username.value());

        given(createReviewService.create(any(), any()))
                .willReturn(Review.fake(productId));

        mockMvc.perform(MockMvcRequestBuilders.post("/reviews")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"tjrxo1234-202301151058\", " +
                                "\"productId\":" + productId.value() + ", " +
                                "\"rate\": 5, " +
                                "\"comment\":\"매우 좋은 상품\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"reviewId\"")
                ));
    }

    @Test
    void whenCreateReviewUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());

        given(createReviewService.create(any(), any())).willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/reviews")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"tjrxo1234-202301151058\", " +
                                "\"productId\": 1, " +
                                "\"rate\": 5, " +
                                "\"comment\":\"매우 좋은 상품\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenSomeOfReviewInformationNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        mockMvc.perform(MockMvcRequestBuilders.post("/reviews")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"\", " +
                                "\"productId\": " +
                                "\"rate\": " +
                                "\"comment\":\"\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateReviewCantFindProduct() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        ProductId productId = new ProductId(9_999_999L);

        given(createReviewService.create(any(), any())).willThrow(ProductNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/reviews")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"tjrxo1234-202301151058\", " +
                                "\"productId\":" + productId.value() + "," +
                                "\"rate\": 5, " +
                                "\"comment\":\"매우 좋은 상품\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPatchReviewSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gamil.com");
        String token = jwtUtil.encode(username.value());

        given(editReviewService.edit(any(), any()))
                .willReturn(Review.fake(username));

        mockMvc.perform(MockMvcRequestBuilders.patch("/reviews")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": \"1\", " +
                                "\"rate\": 5," +
                                "\"comment\":\"아주 좋은 상품\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"reviewId\"")
                ));
    }

    @Test
    void whenCreateReviewRateIsNotValid() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        mockMvc.perform(MockMvcRequestBuilders.post("/reviews")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"tjrxo1234-202301151058\", " +
                                "\"productId\": 1," +
                                "\"rate\": 6, " +
                                "\"comment\":\"매우 좋은 상품\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenSomeOfPatchReviewInformationNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        mockMvc.perform(MockMvcRequestBuilders.patch("/reviews")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" +
                                "\"rate\": 5, " +
                                "\"comment\": \"아주 좋은 상품\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPatchReviewUserNotExists() throws Exception {
        Username username = new Username("notExists@gamil.com");
        String token = jwtUtil.encode(username.value());

        given(editReviewService.edit(any(), any()))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/reviews")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": \"1\", " +
                                "\"rate\": 5," +
                                "\"comment\":\"매우 좋은 상품\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPatchReviewRateIsInvalid() throws Exception {
        Username username = new Username("notExists@gamil.com");
        String token = jwtUtil.encode(username.value());

        mockMvc.perform(MockMvcRequestBuilders.patch("/reviews")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": \"1\", " +
                                "\"rate\": -7," +
                                "\"comment\":\"매우 좋은 상품\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPatchNotHisOwnReview() throws Exception {
        Username username = new Username("someoneElse@gamil.com");
        String token = jwtUtil.encode(username.value());

        given(editReviewService.edit(any(), any()))
                .willThrow(ReviewerNotMatch.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/reviews")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": \"1\", " +
                                "\"rate\": 5," +
                                "\"comment\":\"내 마음대로 바꾼다\"" +
                                "}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenPatchReviewReviewNotFound() throws Exception {
        Username username = new Username("tjrxo1234@gamil.com");
        String token = jwtUtil.encode(username.value());

        given(editReviewService.edit(any(), any()))
                .willThrow(ReviewNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/reviews")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": \"9999999\", " +
                                "\"rate\": 5," +
                                "\"comment\":\"내 마음대로 바꾼다\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteReviewSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 1L;

        given(deleteReviewService.delete(username, id))
                .willReturn(Review.fake(username));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/reviews/%d", id))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"reviewId\"")
                ));
    }

    @Test
    void whenDeleteReviewUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 1L;

        given(deleteReviewService.delete(username, id))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/reviews/%d", id))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDeleteReviewReviewNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 9_999_999L;

        given(deleteReviewService.delete(username, id))
                .willThrow(ReviewNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/reviews/%d", id))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDeleteReviewUsernameNotMatch() throws Exception {
        Username username = new Username("someoneOther@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 1L;

        given(deleteReviewService.delete(username, id))
                .willThrow(ReviewerNotMatch.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/reviews/%d", id))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }
}
