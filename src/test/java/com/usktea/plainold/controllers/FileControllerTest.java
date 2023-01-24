package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.file.FileUploadService;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(FileController.class)
class FileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileUploadService fileUploadService;

    @SpyBean
    private JwtUtil jwtUtil;

    @Test
    void whenUploadSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        MockMultipartFile file = new MockMultipartFile(
                "file", "image.png", MediaType.IMAGE_PNG_VALUE, "image".getBytes());

        String folderName = "review-image";

        given(fileUploadService.upload(file, folderName, username)).willReturn("url");

        mockMvc.perform(MockMvcRequestBuilders.multipart(String.format("/files?folder=%s", folderName))
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"url\"")
                ));
    }

    @Test
    void whenUploadFail() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        MockMultipartFile file = new MockMultipartFile(
                "file", "image.png", MediaType.IMAGE_PNG_VALUE, "image".getBytes());

        String folderName = "review-image";

        given(fileUploadService.upload(file, folderName, username)).willThrow(IllegalArgumentException.class);

        mockMvc.perform(MockMvcRequestBuilders.multipart(String.format("/files?folder=%s", folderName))
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());

        MockMultipartFile file = new MockMultipartFile(
                "file", "image.png", MediaType.IMAGE_PNG_VALUE, "image".getBytes());

        String folderName = "review-image";

        given(fileUploadService.upload(file, folderName, username)).willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.multipart(String.format("/files?folder=%s", folderName))
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }
}
