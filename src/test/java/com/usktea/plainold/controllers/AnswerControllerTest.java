package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.answer.DeleteAnswerService;
import com.usktea.plainold.applications.answer.CreateAnswerService;
import com.usktea.plainold.applications.answer.EditAnswerService;
import com.usktea.plainold.applications.answer.GetAnswerService;
import com.usktea.plainold.dtos.CreateAnswerRequest;
import com.usktea.plainold.dtos.EditAnswerRequest;
import com.usktea.plainold.exceptions.AnswerCannotBeEdited;
import com.usktea.plainold.exceptions.AnswerNotFound;
import com.usktea.plainold.exceptions.InquiryNotExists;
import com.usktea.plainold.exceptions.NotHaveCreateAnswerAuthority;
import com.usktea.plainold.exceptions.NotHaveDeleteAnswerAuthority;
import com.usktea.plainold.exceptions.NotHaveEditAnswerAuthority;
import com.usktea.plainold.models.answer.Answer;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(AnswerController.class)
class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetAnswerService getAnswerService;

    @MockBean
    private CreateAnswerService createAnswerService;

    @MockBean
    private EditAnswerService editAnswerService;

    @MockBean
    private DeleteAnswerService deleteAnswerService;

    @SpyBean
    private JwtUtil jwtUtil;

    @Test
    void whenHasMatchingAnswers() throws Exception {
        Long inquiryId1 = 1L;
        Long inquiryId2 = 2L;

        given(getAnswerService.answers(any()))
                .willReturn(List.of(Answer.fake(inquiryId1)));

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/answers?inquiryIds=%d,%d", inquiryId1, inquiryId2)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"answers\"")
                ));
    }

    @Test
    void whenThereIsNotMatchingAnswer() throws Exception {
        Long inquiryId1 = 3L;
        Long inquiryId2 = 4L;

        given(getAnswerService.answers(any()))
                .willReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/answers?inquiryIds=%d,%d", inquiryId1, inquiryId2)))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenCreateAnswerSuccess() throws Exception {
        Users admin = Users.fake(Role.ADMIN);
        Username adminName = admin.username();

        String token = jwtUtil.encode(adminName.value());

        given(createAnswerService.create(any(Username.class), any(CreateAnswerRequest.class)))
                .willReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/answers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"inquiryId\": 1, " +
                                "\"content\": \"이런 답변\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }

    @Test
    void whenNotAdminTryToCreateAnswer() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        given(createAnswerService.create(any(Username.class), any(CreateAnswerRequest.class)))
                .willThrow(NotHaveCreateAnswerAuthority.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/answers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"inquiryId\": 1, " +
                                "\"content\": \"이런 답변\"" +
                                "}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenTryToAnswerNotExistsInquiry() throws Exception {
        Users admin = Users.fake(Role.ADMIN);
        Username adminName = admin.username();

        String token = jwtUtil.encode(adminName.value());

        given(createAnswerService.create(any(Username.class), any(CreateAnswerRequest.class)))
                .willThrow(InquiryNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/answers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"inquiryId\": 1, " +
                                "\"content\": \"이런 답변\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenSomeOfCreateAnswerInformationNotExists() throws Exception {
        Users admin = Users.fake(Role.ADMIN);
        Username adminName = admin.username();

        String token = jwtUtil.encode(adminName.value());

        mockMvc.perform(MockMvcRequestBuilders.post("/answers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"inquiryId\": 1, " +
                                "\"content\": \"\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEditAnswerSuccess() throws Exception {
        Users admin = Users.fake(Role.ADMIN);
        Username adminName = admin.username();
        Long answerId = 1L;
        String token = jwtUtil.encode(adminName.value());

        given(editAnswerService.edit(any(Username.class), any(EditAnswerRequest.class)))
                .willReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.patch("/answers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" + answerId + ", " +
                                "\"content\": \"수정된 답변\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }

    @Test
    void whenNotAdminTryToEditAnswer() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        Long answerId = 1L;
        String token = jwtUtil.encode(username.value());

        given(editAnswerService.edit(any(Username.class), any(EditAnswerRequest.class)))
                .willThrow(NotHaveEditAnswerAuthority.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/answers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" + answerId + ", " +
                                "\"content\": \"수정된 답변\"" +
                                "}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenEditAnswerNotExists() throws Exception {
        Users admin = Users.fake(Role.ADMIN);
        Username adminName = admin.username();
        Long answerId = 9_999_999L;
        String token = jwtUtil.encode(adminName.value());

        given(editAnswerService.edit(any(Username.class), any(EditAnswerRequest.class)))
                .willThrow(AnswerNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/answers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" + answerId + ", " +
                                "\"content\": \"수정된 답변\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenAnswerIsDeleted() throws Exception {
        Users admin = Users.fake(Role.ADMIN);
        Username adminName = admin.username();
        Long answerId = 1L;
        String token = jwtUtil.encode(adminName.value());

        given(editAnswerService.edit(any(Username.class), any(EditAnswerRequest.class)))
                .willThrow(AnswerCannotBeEdited.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/answers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" + answerId + ", " +
                                "\"content\": \"수정된 답변\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenSomeOfEditInformationNotExists() throws Exception {
        Users admin = Users.fake(Role.ADMIN);
        Username adminName = admin.username();
        Long answerId = 1L;
        String token = jwtUtil.encode(adminName.value());

        mockMvc.perform(MockMvcRequestBuilders.patch("/answers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" + answerId + ", " +
                                "\"content\": \"\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenNotAdminTryToDeleteAnswer() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long answerId = 1L;

        given(deleteAnswerService.delete(any(Username.class), any(Long.class)))
                .willThrow(NotHaveDeleteAnswerAuthority.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/answers/%d", answerId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenTryToDeleteNotExistsAnswer() throws Exception {
        Users admin = Users.fake(Role.ADMIN);
        Username adminName = admin.username();
        String token = jwtUtil.encode(adminName.value());
        Long answerId = 9_999_999L;

        given(deleteAnswerService.delete(any(Username.class), any(Long.class)))
                .willThrow(AnswerNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/answers/%d", answerId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDeleteSuccess() throws Exception {
        Users admin = Users.fake(Role.ADMIN);
        Username adminName = admin.username();
        String token = jwtUtil.encode(adminName.value());
        Long answerId = 1L;

        given(deleteAnswerService.delete(any(Username.class), any(Long.class)))
                .willReturn(answerId);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/answers/%d", answerId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }
}
