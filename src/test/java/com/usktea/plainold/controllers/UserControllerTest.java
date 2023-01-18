package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.GetUserService;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetUserService getUserService;

    @SpyBean
    private JwtUtil jwtUtil;

    @Test
    void whenUserExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");

        String token = jwtUtil.encode(username.value());

        given(getUserService.find(username))
                .willReturn(Users.fake(username));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"username\"")
                ));
    }

    @Test
    void whenUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");

        String token = jwtUtil.encode(username.value());

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }
}
