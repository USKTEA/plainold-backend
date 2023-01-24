package com.usktea.plainold.applications.file;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.usktea.plainold.applications.GetUserService;
import com.usktea.plainold.models.user.Username;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileUploadService {
    private final GetUserService getUserService;
    private final FileService s3Service;

    public FileUploadService(GetUserService getUserService, FileService s3Service) {
        this.getUserService = getUserService;
        this.s3Service = s3Service;
    }

    public String upload(MultipartFile file, String folder, Username username) {
        checkUserIsExists(username);

        String fileName = createFileName(file.getOriginalFilename(), folder);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            s3Service.upload(inputStream, objectMetadata, fileName);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러 발생 (%s)", file.getOriginalFilename()));
        }

        return s3Service.getFileUrl(fileName);
    }

    private void checkUserIsExists(Username username) {
        getUserService.find(username);
    }

    private String createFileName(String originalFilename, String folder) {
        return folder + "/" + UUID.randomUUID().toString().concat(getFileExtension(originalFilename));
    }

    private String getFileExtension(String originalFilename) {
        try {
            return originalFilename.substring(originalFilename.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException exception) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다", originalFilename));
        }
    }
}
