package com.usktea.plainold.applications.file;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class FileUploadServiceTest {
    private GetUserService getUserService;
    private FileService fileService;
    private FileUploadService fileUploadService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        fileService = mock(FileService.class);
        fileUploadService = new FileUploadService(getUserService, fileService);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");

        MockMultipartFile file = new MockMultipartFile(
                "file", "image.png", MediaType.IMAGE_PNG_VALUE, "image".getBytes());

        String folderName = "review-image";

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class, () -> fileUploadService.upload(file, folderName, username));
    }

    @Test
    void whenFileFormatIsInvalid() {
        Username username = new Username("notExists@gmail.com");

        MockMultipartFile file = new MockMultipartFile(
                "file", "image", MediaType.IMAGE_PNG_VALUE, "image".getBytes());

        String folderName = "review-image";

        given(getUserService.find(username)).willReturn(Users.fake(username));

        willThrow(IllegalArgumentException.class).given(fileService).upload(any(), any(), any());

        assertThrows(IllegalArgumentException.class, () -> fileUploadService.upload(file, folderName, username));
    }

    @Test
    void whenFileUploadSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");

        MockMultipartFile file = new MockMultipartFile(
                "file", "image.png", MediaType.IMAGE_PNG_VALUE, "image".getBytes());

        String folderName = "review-image";

        given(getUserService.find(username)).willReturn(Users.fake(username));

        String fileUrl = "url";

        given(fileService.getFileUrl(any())).willReturn(fileUrl);

        String url = fileUploadService.upload(file, folderName, username);

        assertThat(fileUrl).isEqualTo(url);
    }
}
