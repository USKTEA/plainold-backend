package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.AddCartItemService;
import com.usktea.plainold.applications.DeleteCartItemService;
import com.usktea.plainold.applications.GetCartService;
import com.usktea.plainold.applications.UpdateCartItemService;
import com.usktea.plainold.exceptions.CartNotExists;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.cart.Item;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private JwtUtil jwtUtil;

    @MockBean
    private GetCartService getCartService;

    @MockBean
    private CreateCartService createCartService;

    @MockBean
    private AddCartItemService addCartItemService;

    @MockBean
    private UpdateCartItemService updateCartItemService;

    @MockBean
    private DeleteCartItemService deleteCartItemService;

    @Test
    void getCartSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");

        String token = jwtUtil.encode(username.value());

        mockMvc.perform(MockMvcRequestBuilders.get("/carts")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"items\"")
                ));
    }

    @Test
    void whenUserNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");

        String token = jwtUtil.encode(username.value());

        given(getCartService.cart(username)).willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/carts")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCartNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");

        String token = jwtUtil.encode(username.value());

        given(getCartService.cart(username)).willThrow(CartNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/carts")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"items\"")
                ));

        verify(createCartService).create(username);
    }

    @Test
    void addItemSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");

        String token = jwtUtil.encode(username.value());

        given(addCartItemService.addItem(any(), any()))
                .willReturn(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/carts")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"items\": [" +
                                "{" +
                                "\"id\": 1," +
                                "\"productId\": 1," +
                                "\"price\": 10000," +
                                "\"name\": \"T-shirt\"," +
                                "\"thumbnailUrl\": \"http://url.com\"," +
                                "\"shippingFee\": 2500," +
                                "\"freeShippingAmount\": 50000," +
                                "\"quantity\": 1," +
                                "\"totalPrice\": 10000," +
                                "\"option\": { \"size\": \"L\", \"color\": \"Black\" }" +
                                "}" +
                                "]" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"counts\"")
                ));
    }

    @Test
    void addItemFail() throws Exception {
        Username username = new Username("notExists@gmail.com");

        String token = jwtUtil.encode(username.value());

        given(addCartItemService.addItem(any(), any()))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/carts")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"items\": [" +
                                "{" +
                                "\"id\": 1," +
                                "\"productId\": 1," +
                                "\"price\": 10000," +
                                "\"name\": \"T-shirt\"," +
                                "\"thumbnailUrl\": \"http://url.com\"," +
                                "\"shippingFee\": 2500," +
                                "\"freeShippingAmount\": 50000," +
                                "\"quantity\": 1," +
                                "\"totalPrice\": 10000," +
                                "\"option\": { \"size\": \"L\", \"color\": \"Black\" }" +
                                "}" +
                                "]" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateItemSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");

        String token = jwtUtil.encode(username.value());

        ProductId productId = new ProductId(1L);

        given(updateCartItemService.updateItem(any(), any()))
                .willReturn(List.of(productId));

        mockMvc.perform(MockMvcRequestBuilders.patch("/carts")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"items\": [" +
                                "{" +
                                "\"id\": 1," +
                                "\"productId\": 1," +
                                "\"price\": 10000," +
                                "\"name\": \"T-shirt\"," +
                                "\"thumbnailUrl\": \"http://url.com\"," +
                                "\"shippingFee\": 2500," +
                                "\"freeShippingAmount\": 50000," +
                                "\"quantity\": 2," +
                                "\"totalPrice\": 10000," +
                                "\"option\": { \"size\": \"L\", \"color\": \"Black\" }" +
                                "}" +
                                "]" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"updated\"")
                ));
    }

    @Test
    void whenQuantityIsNotValid() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");

        String token = jwtUtil.encode(username.value());

        mockMvc.perform(MockMvcRequestBuilders.patch("/carts")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"items\": [" +
                                "{" +
                                "\"id\": 1," +
                                "\"productId\": 1," +
                                "\"price\": 10000," +
                                "\"name\": \"T-shirt\"," +
                                "\"thumbnailUrl\": \"http://url.com\"," +
                                "\"shippingFee\": 2500," +
                                "\"freeShippingAmount\": 50000," +
                                "\"quantity\": -1," +
                                "\"totalPrice\": 10000," +
                                "\"option\": { \"size\": \"L\", \"color\": \"Black\" }" +
                                "}" +
                                "]" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteItemSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);

        String token = jwtUtil.encode(username.value());

        given(deleteCartItemService.delete(any(), any()))
                .willReturn(List.of(productId));

        mockMvc.perform(MockMvcRequestBuilders.post("/carts/delete")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"items\": [" +
                                "{" +
                                "\"id\": 1," +
                                "\"productId\": 1," +
                                "\"price\": 10000," +
                                "\"name\": \"T-shirt\"," +
                                "\"thumbnailUrl\": \"http://url.com\"," +
                                "\"shippingFee\": 2500," +
                                "\"freeShippingAmount\": 50000," +
                                "\"quantity\": 1," +
                                "\"totalPrice\": 10000," +
                                "\"option\": { \"size\": \"L\", \"color\": \"Black\" }" +
                                "}" +
                                "]" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"deleted\""))
                );
    }

    @Test
    void deleteItemFail() throws Exception {
        Username username = new Username("notExists@gmail.com");
        ProductId productId = new ProductId(1L);
        List<Item> items = List.of(Item.fake(productId));

        String token = jwtUtil.encode(username.value());

        given(deleteCartItemService.delete(any(), any()))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/carts/delete")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"items\": [" +
                                "{" +
                                "\"id\": 1," +
                                "\"productId\": 1," +
                                "\"price\": 10000," +
                                "\"name\": \"T-shirt\"," +
                                "\"thumbnailUrl\": \"http://url.com\"," +
                                "\"shippingFee\": 2500," +
                                "\"freeShippingAmount\": 50000," +
                                "\"quantity\": 1," +
                                "\"totalPrice\": 10000," +
                                "\"option\": { \"size\": \"L\", \"color\": \"Black\" }" +
                                "}" +
                                "]" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
}
