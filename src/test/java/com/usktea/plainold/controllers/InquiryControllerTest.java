package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.GetInquiryService;
import com.usktea.plainold.dtos.GetInquiriesRequestDto;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.inquiry.InquiryView;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
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
}
