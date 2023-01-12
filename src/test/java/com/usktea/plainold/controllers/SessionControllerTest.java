package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.LoginService;
import com.usktea.plainold.dtos.TokenDto;
import com.usktea.plainold.exceptions.LoginFailed;
import com.usktea.plainold.models.user.Password;
import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessionController.class)
@ActiveProfiles("test")
class SessionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @BeforeEach
    void setup() {
        Username username = new Username("tjrxo1234@gmail.com");
        Password password = new Password("Password1234!");

        Username wrongUsername = new Username("notTjrxo1234@gmail.com");
        Password wrongPassword = new Password("notPassword1234!");

        given(loginService.login(username, password))
                .willReturn(TokenDto.fake());

        given(loginService.login(wrongUsername, wrongPassword))
                .willThrow(LoginFailed.class);
    }

    @Test
    void loginSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"username\": \"tjrxo1234@gmail.com\", " +
                                "\"password\": \"Password1234!\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"accessToken\"")
                ));
    }

    @Test
    void loginFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"username\": \"notTjrxo1234@gmail.com\", " +
                                "\"password\": \"notPassword1234!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPasswordIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"username\":\"tjrxo1234@gmail.com\", " +
                                "\"password\": \"xxx\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUsernameIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"username\":\"xxx\", " +
                                "\"password\": \"Password1234!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserNameIsBlank() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"username\":\"\", " +
                                "\"password\": \"Password1234!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPasswordIsBlank() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"username\":\"tjrxo1234@gmail.com\", " +
                                "\"password\": \"\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
}
