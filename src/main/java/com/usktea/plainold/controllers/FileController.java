package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.file.FileUploadService;
import com.usktea.plainold.dtos.FileUploadResultDto;
import com.usktea.plainold.exceptions.FileUploadFail;
import com.usktea.plainold.models.user.Username;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("files")
public class FileController {
    private final FileUploadService fileUploadService;

    public FileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FileUploadResultDto upload(
            @RequestAttribute Username username,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = true) String folder
    ) {
        try {
            String url = fileUploadService.upload(file, folder, username);

            return new FileUploadResultDto(url);
        } catch (Exception exception) {
            throw new FileUploadFail(exception.getMessage());
        }
    }

    @ExceptionHandler(FileUploadFail.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String fileUploadFailed(Exception exception) {
        return exception.getMessage();
    }
}
