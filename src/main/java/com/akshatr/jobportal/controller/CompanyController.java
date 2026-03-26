package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.dto.GeneralAPIResponse;
import com.akshatr.jobportal.model.dto.company.CompanyRequestDto;
import com.akshatr.jobportal.model.dto.company.CompanyResponseDto;
import com.akshatr.jobportal.model.entity.Company;
import com.akshatr.jobportal.service.CompanyService;
import com.akshatr.jobportal.service.cache.CacheService;
import com.akshatr.jobportal.util.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CacheService cacheService;
    private final ExceptionHandler exceptionHandler;

    private static final String CACHE_NAME = "COMPANY";

    @GetMapping("/{id}")
    public ResponseEntity<GeneralAPIResponse> getCompany(@PathVariable Long id){
        try {
            CompanyResponseDto company = (CompanyResponseDto) cacheService.get(CACHE_NAME, id.toString());
            if (company == null) {
                company = generateCompanyResponse(companyService.getCompany(id));
                cacheService.add(CACHE_NAME, id.toString(), company);
            }

            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(company)
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    @GetMapping
    public ResponseEntity<GeneralAPIResponse> getCompanies(){
        try {
            List<CompanyResponseDto> companyList = (List<CompanyResponseDto>) cacheService.get(CACHE_NAME, "ALL");
            if (companyList == null) {
                companyList = companyService.getCompanies().stream()
                        .map(this::generateCompanyResponse)
                        .toList();
                cacheService.add(CACHE_NAME, "ALL", companyList);
            }

            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(companyList)
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<GeneralAPIResponse> saveCompany(@RequestBody CompanyRequestDto request){
        try {
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(generateCompanyResponse(companyService.saveCompany(request)))
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
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
