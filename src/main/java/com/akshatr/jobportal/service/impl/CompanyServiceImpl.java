package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.company.CompanyRequestDto;
import com.akshatr.jobportal.model.entity.Company;
import com.akshatr.jobportal.repository.CompanyRepository;
import com.akshatr.jobportal.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public Company getCompany(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isEmpty()){
            throw new RuntimeException("Company not found.");
        }

        return company.get();
    }

    @Override
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company saveCompany(CompanyRequestDto request) {
        Company company = new Company();

        Optional<Company> existingCompany = companyRepository.findById(request.getId());
        if(existingCompany.isPresent()){
            company = existingCompany.get();
        }

        company.setName(request.getName());

        return companyRepository.save(company);
    }
}
