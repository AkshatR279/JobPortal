package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationRequestDto;
import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationResponseDto;
import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationSearchRequest;
import com.akshatr.jobportal.model.entity.JobApplication;

import java.util.List;

public interface JobApplicationService {
    public List<JobApplication> getJobApplications(JobApplicationSearchRequest request);
    public JobApplication saveJobApplication(JobApplicationRequestDto request);
}
