package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.inquiry.DeleteInquiryService;
import com.usktea.plainold.applications.inquiry.CreateInquiryService;
import com.usktea.plainold.applications.inquiry.EditInquiryService;
import com.usktea.plainold.applications.inquiry.GetInquiryService;
import com.usktea.plainold.dtos.CreateInquiryRequest;
import com.usktea.plainold.dtos.EditInquiryRequest;
import com.usktea.plainold.dtos.GetInquiriesRequestDto;
import com.usktea.plainold.exceptions.GuestIsNotAuthorized;
import com.usktea.plainold.exceptions.InquiryCannotBeEdited;
import com.usktea.plainold.exceptions.InquiryNotExists;
import com.usktea.plainold.exceptions.NotHaveDeleteInquiryAuthority;
import com.usktea.plainold.exceptions.NotHaveEditInquiryAuthority;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.inquiry.InquiryView;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InquiryController.class)
@ActiveProfiles("test")
class InquiryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetInquiryService getInquiryService;

    @MockBean
    private CreateInquiryService createInquiryService;

    @MockBean
    private EditInquiryService editInquiryService;

    @MockBean
    private DeleteInquiryService deleteInquiryService;

    @SpyBean
    private JwtUtil jwtUtil;

    @Test
    void whenInquiriesNotExists() throws Exception {
        ProductId productId = new ProductId(1L);
        Integer pageNumber = 1;

        Page<InquiryView> page = new PageImpl<>(List.of());

        given(getInquiryService.inquiries(any(GetInquiriesRequestDto.class)))
                .willReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get(
                        String.format("/inquiries?productId=%d&page=%d", productId.value(), pageNumber)))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenProductNotExists() throws Exception {
        ProductId productId = new ProductId(9_999_999L);
        Integer pageNumber = 1;

        given(getInquiryService.inquiries(any(GetInquiriesRequestDto.class)))
                .willThrow(ProductNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.get(
                        String.format("/inquiries?productId=%d&page=%d", productId.value(), pageNumber)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInquiriesExists() throws Exception {
        ProductId productId = new ProductId(1L);
        Integer pageNumber = 1;

        Page<InquiryView> page =
                new PageImpl<>(List.of(InquiryView.fake(productId.value())));

        given(getInquiryService.inquiries(any(GetInquiriesRequestDto.class)))
                .willReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get(
                        String.format("/inquiries?productId=%d&page=%d", productId.value(), pageNumber)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"inquiries\"")
                ));
    }

    @Test
    void whenCreateInquirySuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);
        String token = jwtUtil.encode(username.value());

        given(createInquiryService.create(eq(username), any(CreateInquiryRequest.class)))
                .willReturn(any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":" + productId.value() + ", " +
                                "\"type\": \"PUBLIC\", " +
                                "\"title\": \"사이즈 문의\", " +
                                "\"content\": \"이렇게 입으면 될까요\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }

    @Test
    void whenCreateInquiryUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        ProductId productId = new ProductId(1L);
        String token = jwtUtil.encode(username.value());

        given(createInquiryService.create(eq(username), any(CreateInquiryRequest.class)))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":" + productId.value() + ", " +
                                "\"type\": \"PUBLIC\", " +
                                "\"title\": \"사이즈 문의\", " +
                                "\"content\": \"이렇게 입으면 될까요\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGuestTyrToCreateInquiry() throws Exception {
        ProductId productId = new ProductId(1L);

        given(createInquiryService.create(any(), any(CreateInquiryRequest.class)))
                .willThrow(GuestIsNotAuthorized.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/inquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":" + productId.value() + ", " +
                                "\"type\": \"PUBLIC\", " +
                                "\"title\": \"사이즈 문의\", " +
                                "\"content\": \"이렇게 입으면 될까요\"" +
                                "}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenCreateInquiryProductNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(9_999_999L);

        String token = jwtUtil.encode(username.value());

        given(createInquiryService.create(eq(username), any(CreateInquiryRequest.class)))
                .willThrow(ProductNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":" + productId.value() + ", " +
                                "\"type\": \"PUBLIC\", " +
                                "\"title\": \"사이즈 문의\", " +
                                "\"content\": \"이렇게 입으면 될까요\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateInquirySomeOfInformationNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);

        String token = jwtUtil.encode(username.value());

        mockMvc.perform(MockMvcRequestBuilders.post("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":" + productId.value() + ", " +
                                "\"type\": \"PUBLIC\", " +
                                "\"title\": \"\", " +
                                "\"content\": \"이렇게 입으면 될까요\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEditInquiryUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 1L;

        given(editInquiryService.edit(eq(username), any(EditInquiryRequest.class)))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" + id + ", " +
                                "\"title\": \"제목을 이렇게 수정\", " +
                                "\"content\": \"내용을 이렇게 수정\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenOtherUserTryToEditInquiry() throws Exception {
        Username otherUsername = new Username("otherUser@gmail.com");
        String token = jwtUtil.encode(otherUsername.value());
        Long id = 1L;

        given(editInquiryService.edit(eq(otherUsername), any(EditInquiryRequest.class)))
                .willThrow(NotHaveEditInquiryAuthority.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" + id + ", " +
                                "\"title\": \"제목을 이렇게 수정\", " +
                                "\"content\": \"내용을 이렇게 수정\"" +
                                "}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenTryToEditNotExistsInquiry() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 9_999_999L;

        given(editInquiryService.edit(eq(username), any(EditInquiryRequest.class)))
                .willThrow(InquiryNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" + id + ", " +
                                "\"title\": \"제목을 이렇게 수정\", " +
                                "\"content\": \"내용을 이렇게 수정\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenSomeOfRequestDataNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.patch("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" + id + ", " +
                                "\"title\": \"\", " +
                                "\"content\": \"내용을 이렇게 수정\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInquiryCannotBeEdited() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 1L;

        given(editInquiryService.edit(eq(username), any(EditInquiryRequest.class)))
                .willThrow(InquiryCannotBeEdited.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" + id + ", " +
                                "\"title\": \"제목을 이렇게 수정\", " +
                                "\"content\": \"내용을 이렇게 수정\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEditInquirySuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 1L;

        given(editInquiryService.edit(eq(username), any(EditInquiryRequest.class)))
                .willReturn(id);

        mockMvc.perform(MockMvcRequestBuilders.patch("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" + id + ", " +
                                "\"title\": \"제목을 이렇게 수정\", " +
                                "\"content\": \"내용을 이렇게 수정\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }

    @Test
    void whenAdminEditInquiry() throws Exception {
        Users admin = Users.fake(Role.ADMIN);
        Username adminName = admin.username();

        String token = jwtUtil.encode(adminName.value());
        Long id = 1L;

        given(editInquiryService.edit(eq(adminName), any(EditInquiryRequest.class)))
                .willReturn(id);

        mockMvc.perform(MockMvcRequestBuilders.patch("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":" + id + ", " +
                                "\"title\": \"제목을 이렇게 수정\", " +
                                "\"content\": \"내용을 이렇게 수정\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }

    @Test
    void whenDeleteInquiryUserNotExist() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 1L;

        given(deleteInquiryService.delete(username, id)).willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/inquiries/%d", id))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenTryToDeleteNotExistsInquiry() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 9_999_999L;

        given(deleteInquiryService.delete(username, id)).willThrow(InquiryNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/inquiries/%d", id))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserTryToDeleteNotHisOwnInquiry() throws Exception {
        Username otherUser = new Username("otherUser@gmail.com");
        String token = jwtUtil.encode(otherUser.value());
        Long id = 1L;

        given(deleteInquiryService.delete(otherUser, id))
                .willThrow(NotHaveDeleteInquiryAuthority.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/inquiries/%d", id))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenAdminDeleteInquiry() throws Exception {
        Users admin = Users.fake(Role.ADMIN);
        Username adminName = admin.username();
        Long id = 1L;
        String token = jwtUtil.encode(adminName.value());

        given(deleteInquiryService.delete(adminName, id)).willReturn(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/inquiries/%d", id))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }

    @Test
    void whenDeleteInquirySuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        Long id = 1L;

        given(deleteInquiryService.delete(username, id))
                .willReturn(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/inquiries/%d", id))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }
}
