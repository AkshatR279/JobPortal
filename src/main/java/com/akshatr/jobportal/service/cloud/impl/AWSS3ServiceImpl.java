package com.akshatr.jobportal.service.cloud.impl;

import com.akshatr.jobportal.exceptions.BadRequestException;
import com.akshatr.jobportal.model.enums.FileType;
import com.akshatr.jobportal.service.cloud.AWSS3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

@Service
public class AWSS3ServiceImpl implements AWSS3Service {
    private final S3Client s3Client;
    private final String bucket;
    private final Map<FileType, String> fileDirectory;

    public AWSS3ServiceImpl(S3Client s3Client, @Value("${cloud.aws.bucket.name}") String bucket){
        this.s3Client = s3Client;
        this.bucket =  bucket;

        this.fileDirectory =  new EnumMap<>(FileType.class);
        this.fileDirectory.put(FileType.COMPANY, "companies/");
        this.fileDirectory.put(FileType.JOB, "jobs/");
        this.fileDirectory.put(FileType.ORDER, "orders/");
        this.fileDirectory.put(FileType.PAYMENT, "payments/");
    }

    public String uploadFile(FileType fileType, MultipartFile file) throws IOException {
        if(file == null){
            throw new BadRequestException("Invalid file.");
        }

        String contentType = file.getContentType();
        if(contentType == null || contentType.split("/").length < 2){
            throw new BadRequestException("Invalid file.");
        }

        String filename = generateFileName(fileType,  contentType.split("/")[1]);

        PutObjectRequest uploadReq = PutObjectRequest.builder()
                .bucket(bucket)
                .key(filename)
                .build();

        s3Client.putObject(uploadReq, RequestBody.fromBytes(file.getBytes()));

        return filename;
    }

    private String generateFileName(FileType filetype, String fileExtension){
        String directory = "";
        if(fileDirectory.containsKey(filetype)){
            directory = fileDirectory.get(filetype);
        }

        return directory + filetype.name() + "_" + System.currentTimeMillis() + "." + fileExtension;
    }
}
