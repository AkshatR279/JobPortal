package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.dto.job.JobResponseShortDto;
import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationRequestDto;
import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationResponseDto;
import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationSearchRequest;
import com.akshatr.jobportal.model.dto.user.UserResponseShortDto;
import com.akshatr.jobportal.model.entity.Job;
import com.akshatr.jobportal.model.entity.JobApplication;
import com.akshatr.jobportal.model.entity.User;
import com.akshatr.jobportal.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    @PostMapping("/search")
    public List<JobApplicationResponseDto> searchJobApplications(@RequestBody JobApplicationSearchRequest request){
        return jobApplicationService.getJobApplications(request).stream()
                .map(this::convertJobApplicationToResponseDto)
                .toList();
    }

    @PostMapping("/save")
    public JobApplicationResponseDto saveJobApplication(@RequestBody JobApplicationRequestDto request){
        return convertJobApplicationToResponseDto(jobApplicationService.saveJobApplication(request));
    }

    private JobApplicationResponseDto convertJobApplicationToResponseDto(JobApplication jobApplication){
        return JobApplicationResponseDto.builder()
                .id(jobApplication.getId())
                .name(jobApplication.getName())
                .job(convertToJobShort(jobApplication.getJob()))
                .user(convertToUserShort(jobApplication.getUser()))
                .status(jobApplication.getStatus())
                .submittedOn(jobApplication.getSubmittedOn())
                .createdOn(jobApplication.getCreatedOn())
                .lastUpdatedOn(jobApplication.getLastUpdatedOn())
                .build();
    }

    private JobResponseShortDto convertToJobShort(Job job){
        return JobResponseShortDto.builder()
                .id(job.getId())
                .name(job.getName())
                .build();
    }

    private UserResponseShortDto convertToUserShort(User user){
        return UserResponseShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
