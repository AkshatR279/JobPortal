package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.dto.company.CompanyRequestDto;
import com.akshatr.jobportal.model.dto.company.CompanyResponseDto;
import com.akshatr.jobportal.model.entity.Company;
import com.akshatr.jobportal.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/{id}")
    @Cacheable(value = "COMPANY", key = "#id")
    public CompanyResponseDto getCompany(@PathVariable Long id){
        return generateCompanyResponse(companyService.getCompany(id));
    }

    @GetMapping
    @Cacheable(value = "COMPANIES", key = "'All'")
    public List<CompanyResponseDto> getCompanies(){
        return companyService.getCompanies().stream()
                .map(this::generateCompanyResponse)
                .toList();
    }

    @PostMapping("/save")
    @CachePut(value = "COMPANY", key = "#result.id")
    public CompanyResponseDto saveCompany(@RequestBody CompanyRequestDto request){
        return generateCompanyResponse(companyService.saveCompany(request));
    }

    private CompanyResponseDto generateCompanyResponse(Company company){
        return CompanyResponseDto.builder()
                .id(company.getId())
                .name(company.getName())
                .createdOn(company.getCreatedOn())
                .lastUpdatedOn(company.getLastUpdatedOn())
                .build();
    }
}
