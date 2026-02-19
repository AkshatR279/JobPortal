package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.dto.job.JobResponseDto;
import com.akshatr.jobportal.model.entity.Job;
import com.akshatr.jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @GetMapping
    public List<JobResponseDto> getJobs(){
        return jobService.getJobs().stream()
                .map(this::convertJobToDto)
                .toList();
    }

    private JobResponseDto convertJobToDto(Job job){
        return JobResponseDto.builder()
                .id(job.getId())
                .name(job.getName())
                .createdOn(job.getCreatedOn())
                .lastUpdatedOn(job.getLastUpdatedOn())
                .salaryMin(job.getSalaryMin())
                .salaryMax(job.getSalaryMax())
                .experienceMin(job.getExperienceMin())
                .experienceMax(job.getExperienceMax())
                .status(job.getStatus())
                .postedOn(job.getPostedOn())
                .expiredOn(job.getExpiredOn())
                .build();
    }
}
