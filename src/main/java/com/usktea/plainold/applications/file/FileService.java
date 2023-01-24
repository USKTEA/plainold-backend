package com.usktea.plainold.applications.file;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface FileService {
    void upload(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);

    String getFileUrl(String fileName);
}
