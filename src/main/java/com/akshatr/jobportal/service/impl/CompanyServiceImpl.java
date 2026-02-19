package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.entity.Company;
import com.akshatr.jobportal.repository.CompanyRepository;
import com.akshatr.jobportal.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }
}
