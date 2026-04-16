package com.akshatr.jobportal.service.cloud;

import com.akshatr.jobportal.model.enums.FileType;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

public interface AWSS3Service {
    public String uploadFile(FileType fileType, MultipartFile file) throws IOException;
}
