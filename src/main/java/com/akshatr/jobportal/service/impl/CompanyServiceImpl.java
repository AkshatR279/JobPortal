package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.company.CompanyRequestDto;
import com.akshatr.jobportal.model.entity.Company;
import com.akshatr.jobportal.model.enums.FileType;
import com.akshatr.jobportal.repository.CompanyRepository;
import com.akshatr.jobportal.service.CompanyService;
import com.akshatr.jobportal.service.cloud.AWSS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final AWSS3Service fileUploadService;

    @Override
    public Company getCompany(Long id) {
        System.out.println("Service called.");
        Optional<Company> company = companyRepository.findById(id);
        if(company.isEmpty()){
            throw new RuntimeException("Company not found.");
        }

        return company.get();
    }

    @Override
    public List<Company> getCompanies() {
        System.out.println("Service called.");
        return companyRepository.findAll();
    }

    @Override
    public Company saveCompany(CompanyRequestDto request, MultipartFile image) {
        System.out.println("Service called.");

        Company company = new Company();
        Optional<Company> existingCompany = companyRepository.findById(request.getId());
        if(existingCompany.isPresent()){
            company = existingCompany.get();
        }

        company.setName(request.getName());
        if(image != null){
            try {
                company.setImage(fileUploadService.uploadFile(FileType.COMPANY, image));
            } catch (IOException e) {
                throw new RuntimeException("Filed to upload file.");
            }
        }

        return companyRepository.save(company);
    }
}
