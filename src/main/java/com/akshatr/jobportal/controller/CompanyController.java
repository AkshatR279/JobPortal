package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.dto.company.CompanyRequestDto;
import com.akshatr.jobportal.model.dto.company.CompanyResponseDto;
import com.akshatr.jobportal.model.entity.Company;
import com.akshatr.jobportal.service.CompanyService;
import com.akshatr.jobportal.service.cache.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CacheService cacheService;

    private static final String CACHE_NAME = "COMPANY";

    @GetMapping("/{id}")
    public CompanyResponseDto getCompany(@PathVariable Long id){
        CompanyResponseDto company = (CompanyResponseDto) cacheService.get(CACHE_NAME, id.toString());
        if(company == null){
            company = generateCompanyResponse(companyService.getCompany(id));
            cacheService.add(CACHE_NAME, id.toString(), company);
        }

        return company;
    }

    @GetMapping
    public List<CompanyResponseDto> getCompanies(){
        List<CompanyResponseDto> companyList = (List<CompanyResponseDto>) cacheService.get(CACHE_NAME, "ALL");
        if(companyList == null){
            companyList = companyService.getCompanies().stream()
                    .map(this::generateCompanyResponse)
                    .toList();
            cacheService.add(CACHE_NAME, "ALL", companyList);
        }

        return companyList;
    }

    @PostMapping("/save")
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
