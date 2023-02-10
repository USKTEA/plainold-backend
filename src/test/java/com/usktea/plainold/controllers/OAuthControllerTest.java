package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.oAuth.OAuthService;
import com.usktea.plainold.applications.oAuth.OAuthServiceFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(OAuthController.class)
class OAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OAuthServiceFactory oAuthServiceFactory;

    @MockBean
    private OAuthService oAuthService;

    @Test
    void getRedirectUrlSuccess() throws Exception {
        String provider = "kakao";

        given(oAuthService.getAuthorizationUrl()).willReturn("url");

        given(oAuthServiceFactory.getOAuthService(any()))
                .willReturn(oAuthService);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/oauth/%s", provider)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"redirectUrl\"")
                ));
    }

    @Test
    void getRedirectUrlFailed() throws Exception {
        String provider = "INVALID";

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/oauth/%s", provider)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginSuccess() throws Exception {
        String provider = "kakao";
        String code = "CODE";

        given(oAuthService.login(any())).willReturn("ACCESSTOKEN");
        given(oAuthServiceFactory.getOAuthService(any()))
                .willReturn(oAuthService);

        mockMvc.perform(MockMvcRequestBuilders.post("/oauth/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"provider\":\"" + provider + "\", " +
                                "\"code\":\"" + code + "\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"accessToken\"")
                ));
    }

    @Test
    void loginFailed() throws Exception {
        String provider = "INVALID";

        mockMvc.perform(MockMvcRequestBuilders.post("/oauth/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"provider\": \"" + provider + "\" , " +
                                "\"code\":\"CODE\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
}
