package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.product.GetProductDetailService;
import com.usktea.plainold.applications.product.GetProductsService;
import com.usktea.plainold.dtos.ProductDetail;
import com.usktea.plainold.exceptions.CategoryNotFound;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.category.CategoryId;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetProductsService getProductsService;

    @MockBean
    private GetProductDetailService getProductDetailService;

    @Test
    void listWithoutCategory() throws Exception {
        ProductId productId = new ProductId(1L);

        Page<Product> page = new PageImpl<>(List.of(Product.fake(productId)));

        given(getProductsService.list(any(CategoryId.class), any(Integer.class)))
                .willReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"products\":[")
                ));
    }

    @Test
    void listWithCategory() throws Exception {
        ProductId productId = new ProductId(1L);

        Page<Product> page = new PageImpl<>(List.of(Product.fake(productId)));

        given(getProductsService.list(any(CategoryId.class), any(Integer.class)))
                .willReturn(page);

        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/products?categoryId=%d", id)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"products\":[")
                ));
    }

    @Test
    void whenCategoryIsNotExists() throws Exception {
        given(getProductsService.list(eq(new CategoryId(9_999_999L)), any(Integer.class)))
                .willThrow(CategoryNotFound.class);

        Long id = 9_999_999L;

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/products?categoryId=%d", id)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindProductSuccess() throws Exception {
        ProductId productId = new ProductId(1L);

        given(getProductDetailService.detail(productId))
                .willReturn(ProductDetail.fake(productId));

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/products/%d", 1)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ));
    }

    @Test
    void whenFindProductFail() throws Exception {
        ProductId productId = new ProductId(9_999_999L);

        given(getProductDetailService.detail(productId))
                .willThrow(ProductNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/products/%d", 9999999)))
                .andExpect(status().isBadRequest());
    }
}
