package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.GetProductService;
import com.usktea.plainold.models.CategoryId;
import com.usktea.plainold.models.Product;
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

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetProductService getProductService;

    @Test
    void listWithoutCategory() throws Exception {
        Page<Product> page = new PageImpl<>(List.of(Product.fake(1L)));

        given(getProductService.list(any(CategoryId.class), any(Integer.class)))
                .willReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"products\":[")
                ));
    }

    @Test
    void listWithCategory() throws Exception {
        Page<Product> page = new PageImpl<>(List.of(Product.fake(1L)));

        given(getProductService.list(any(CategoryId.class), any(Integer.class)))
                .willReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/products?categoryId=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"products\":[")
                ));
    }
}
