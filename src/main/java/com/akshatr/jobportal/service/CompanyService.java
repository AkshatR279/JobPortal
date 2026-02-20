package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.dto.company.CompanyRequestDto;
import com.akshatr.jobportal.model.entity.Company;

import java.util.List;

public interface CompanyService {
    public List<Company> getCompanies();
    public Company saveCompany(CompanyRequestDto request);
}
