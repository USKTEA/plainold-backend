package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.reply.CreateReplyService;
import com.usktea.plainold.applications.reply.DeleteReplyService;
import com.usktea.plainold.applications.reply.EditReplyService;
import com.usktea.plainold.applications.reply.GetReplyService;
import com.usktea.plainold.dtos.EditReplyRequest;
import com.usktea.plainold.exceptions.ReplierNotMatch;
import com.usktea.plainold.exceptions.ReplyNotExists;
import com.usktea.plainold.exceptions.ReviewNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.reply.Reply;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReplyController.class)
@ActiveProfiles("test")
class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetReplyService getReplyService;

    @MockBean
    private CreateReplyService createReplyService;

    @MockBean
    private EditReplyService editReplyService;

    @MockBean
    private DeleteReplyService deleteReplyService;

    @SpyBean
    private JwtUtil jwtUtil;

    @Test
    void requestRepliesSuccess() throws Exception {
        Long reviewId1 = 1L;
        Long reviewId2 = 2L;

        List<Long> reviewIds = List.of(reviewId1, reviewId2);

        given(getReplyService.list(reviewIds))
                .willReturn(List.of(Reply.fake(1L)));

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/replies?reviewIds=%d,%d", reviewId1, reviewId2)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"replies\"")
                ));
    }

    @Test
    void whenRepliesNotExists() throws Exception {
        Long reviewId1 = 3L;
        Long reviewId2 = 4L;

        List<Long> reviewIds = List.of(reviewId1, reviewId2);

        given(getReplyService.list(reviewIds))
                .willReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/replies?reviewIds=%d,%d", reviewId1, reviewId2)))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenCreateReplySuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long replyId = 1L;

        given(createReplyService.create(any(), any()))
                .willReturn(replyId);

        mockMvc.perform(MockMvcRequestBuilders.post("/replies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"reviewId\": \"1\", " +
                                "\"parent\": null, " +
                                "\"comment\": \"이것은 댓글\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }

    @Test
    void whenCreateReplyUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");

        String token = jwtUtil.encode(username.value());

        given(createReplyService.create(eq(username), any()))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/replies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"reviewId\": \"1\", " +
                                "\"parent\": null, " +
                                "\"comment\": \"이것은 댓글\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateReplyReviewIdNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        mockMvc.perform(MockMvcRequestBuilders.post("/replies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"reviewId\": \"\", " +
                                "\"parent\": null, " +
                                "\"comment\": \"이것은 댓글\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateReplyReviewIsNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long reviewId = 9_999_999L;

        given(createReplyService.create(any(), any()))
                .willThrow(ReviewNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/replies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"reviewId\": \"" + reviewId + "\", " +
                                "\"parent\": null, " +
                                "\"comment\": \"이것은 댓글\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEditReplyUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());

        Long replyId = 1L;
        Long reviewId = 1L;

        given(editReplyService.edit(any(), any())).willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/replies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": " + replyId + ", " +
                                "\"reviewId\": " + reviewId + ", " +
                                "\"comment\": \"좋습니다\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserTryToEditNotHisOwnReply() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        Long replyId = 1L;
        Long reviewId = 1L;

        given(editReplyService.edit(any(), any()))
                .willThrow(ReplierNotMatch.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/replies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": " + replyId + ", " +
                                "\"reviewId\": " + reviewId + ", " +
                                "\"comment\": \"좋습니다\"" +
                                "}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenEditReplyReplyNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        Long replyId = 9_999_999L;
        Long reviewId = 1L;

        given(editReplyService.edit(any(), any())).willThrow(ReplyNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/replies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": " + replyId + ", " +
                                "\"reviewId\": " + reviewId + ", " +
                                "\"comment\": \"좋습니다\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEditReplyReviewNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        Long replyId = 1L;
        Long reviewId = 9_999_999L;

        given(editReplyService.edit(any(), any())).willThrow(ReviewNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/replies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": " + replyId + ", " +
                                "\"reviewId\": " + reviewId + ", " +
                                "\"comment\": \"좋습니다\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEditReplyCommentNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        mockMvc.perform(MockMvcRequestBuilders.patch("/replies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": 1, " +
                                "\"reviewId\": 1, " +
                                "\"comment\": \"\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEditReplySuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        Long replyId = 1L;
        Long reviewId = 1L;
        EditReplyRequest editReplyRequest = EditReplyRequest.fake(replyId, reviewId);

        given(editReplyService.edit(username, editReplyRequest)).willReturn(replyId);

        mockMvc.perform(MockMvcRequestBuilders.patch("/replies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": " + replyId + ", " +
                                "\"reviewId\":" + reviewId + ", " +
                                "\"comment\": \"아주 좋아보이네요\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }

    @Test
    void whenDeleteReplyUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long replyId = 1L;

        given(deleteReplyService.delete(username, replyId)).willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/replies/%d", replyId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDeleteNotExistedReply() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long replyId = 9_999_999L;

        given(deleteReplyService.delete(username, replyId)).willThrow(ReplyNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/replies/%d", replyId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDeleteNotHisOwnReply() throws Exception {
        Username otherUsername = new Username("otherUser@gmail.com");
        String token = jwtUtil.encode(otherUsername.value());
        Long replyId = 1L;

        given(deleteReplyService.delete(any(), any())).willThrow(ReplierNotMatch.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/replies/%d", replyId))
                        .header("Authorization", "Bearer" + token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenDeleteSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long replyId = 1L;

        given(deleteReplyService.delete(username, replyId)).willReturn(replyId);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/replies/%d", replyId))
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));

    }
}
