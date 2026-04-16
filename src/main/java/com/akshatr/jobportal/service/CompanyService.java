package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.dto.company.CompanyRequestDto;
import com.akshatr.jobportal.model.entity.Company;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyService {
    public Company getCompany(Long id);
    public List<Company> getCompanies();
    public Company saveCompany(CompanyRequestDto request, MultipartFile image);
}
