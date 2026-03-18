package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.dto.GeneralAPIResponse;
import com.akshatr.jobportal.model.dto.company.CompanyResponseDto;
import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationRequestDto;
import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationResponseDto;
import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationSearchRequest;
import com.akshatr.jobportal.model.entity.JobApplication;
import com.akshatr.jobportal.service.JobApplicationService;
import com.akshatr.jobportal.service.cache.CacheService;
import com.akshatr.jobportal.util.DtoConvertor;
import com.akshatr.jobportal.util.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    private final DtoConvertor dtoConvertor;
    private final CacheService cache;
    private final ExceptionHandler exceptionHandler;

    private static final String CACHE_NAME = "JOB_APPLICATION";

    @GetMapping
    public ResponseEntity<GeneralAPIResponse> getJobApplications(){
        try {
            List<JobApplicationResponseDto> jobApplicationList = (List<JobApplicationResponseDto>) cache.get(CACHE_NAME, "ALL");
            if (jobApplicationList == null) {
                jobApplicationList = jobApplicationService.getAllJobApplications().stream()
                        .map(this::convertJobApplicationToResponseDto)
                        .toList();
                cache.add(CACHE_NAME, "ALL", jobApplicationList);
            }

            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(jobApplicationList)
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralAPIResponse> getJobApplication(@PathVariable Long id){
        try {
            JobApplicationResponseDto jobApplication = (JobApplicationResponseDto) cache.get(CACHE_NAME, id.toString());
            if (jobApplication == null) {
                jobApplication = convertJobApplicationToResponseDto(jobApplicationService.getJobApplication(id));
                cache.add(CACHE_NAME, id.toString(), jobApplication);
            }

            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(jobApplication)
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<GeneralAPIResponse> saveJobApplication(@RequestBody JobApplicationRequestDto request){
        try {
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(convertJobApplicationToResponseDto(jobApplicationService.saveJobApplication(request)))
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<GeneralAPIResponse> searchJobApplications(@RequestBody JobApplicationSearchRequest request){
        try {
            long job = 0L, user = 0L;
            if(request.getJobId() != null){
                job = request.getJobId();
            }
            if(request.getUserId() != null){
                user = request.getUserId();
            }

            List<JobApplicationResponseDto> jobApplicationList = (List<JobApplicationResponseDto>) cache.get(CACHE_NAME, "SEARCH?J=" + job + "&U=" + user);
            if (jobApplicationList == null) {
                jobApplicationList = jobApplicationService.getJobApplications(request).stream()
                        .map(this::convertJobApplicationToResponseDto)
                        .toList();
                cache.add(CACHE_NAME, "SEARCH?J=" + job + "&U=" + user, jobApplicationList);
            }
            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .data(jobApplicationList)
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }

    private JobApplicationResponseDto convertJobApplicationToResponseDto(JobApplication jobApplication){
        return JobApplicationResponseDto.builder()
                .id(jobApplication.getId())
                .name(jobApplication.getName())
                .job(dtoConvertor.convertToJobShort(jobApplication.getJob()))
                .userId(jobApplication.getUserId())
                .referral(jobApplication.getReferral() != null ? dtoConvertor.convertReferralToResponseShort(jobApplication.getReferral()) : null)
                .status(jobApplication.getStatus())
                .submittedOn(jobApplication.getSubmittedOn())
                .createdOn(jobApplication.getCreatedOn())
                .lastUpdatedOn(jobApplication.getLastUpdatedOn())
                .build();
    }
}
