package com.akshatr.jobportal.service.cloud.impl;

import com.akshatr.jobportal.exceptions.BadRequestException;
import com.akshatr.jobportal.model.enums.FileType;
import com.akshatr.jobportal.service.cloud.AWSS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class AWSS3ServiceImpl implements AWSS3Service {
    private final S3Client s3Client;
    private final String bucket;
    private final String folderCompany;

    public AWSS3ServiceImpl(S3Client s3Client, @Value("${cloud.aws.bucket.name}") String bucket, @Value("${cloud.aws.folder.company}") String folderCompany){
        this.s3Client = s3Client;
        this.bucket =  bucket;
        this.folderCompany =  folderCompany;
    }

    public void uploadFile(FileType fileType, MultipartFile file) throws IOException {
        if(file == null){
            throw new BadRequestException("Invalid file.");
        }

        String contentType = file.getContentType();
        if(contentType == null || contentType.split("/").length < 2){
            throw new BadRequestException("Invalid file.");
        }

        PutObjectRequest uploadReq = PutObjectRequest.builder()
                .bucket(bucket)
                .key(generateFileName(fileType,  contentType.split("/")[1]))
                .build();

        s3Client.putObject(uploadReq, RequestBody.fromBytes(file.getBytes()));
    }

    private String generateFileName(FileType filetype, String fileExtension){
        return filetype.name() + "_" + System.currentTimeMillis() + "." + fileExtension;
    }
}
