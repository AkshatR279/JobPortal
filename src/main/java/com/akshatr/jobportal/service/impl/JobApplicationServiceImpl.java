package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationRequestDto;
import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationSearchRequest;
import com.akshatr.jobportal.model.dto.user.UserResponseDto;
import com.akshatr.jobportal.model.entity.Job;
import com.akshatr.jobportal.model.entity.JobApplication;
import com.akshatr.jobportal.repository.JobApplicationRepository;
import com.akshatr.jobportal.repository.JobRepository;
import com.akshatr.jobportal.service.JobApplicationService;
import com.akshatr.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final UserService userService;

    @Override
    public List<JobApplication> getJobApplications(JobApplicationSearchRequest request) {
        Long jobId = 0L, userId = 0L;
        if(request.getJobId()>0){
            Optional<Job> job = jobRepository.findById(request.getJobId());
            if(job.isEmpty()){
                throw new RuntimeException("Job not found.");
            }
            jobId = job.get().getId();
        }

        if(request.getUserId()>0){
            UserResponseDto user = userService.findById(request.getUserId());

            if(user == null){
                throw new RuntimeException("User not found.");
            }
            userId = user.getId();
        }

        if((jobId > 0) && (userId > 0)){
            return getJobApplicationsBySearch(jobId, userId);
        }
        else if(jobId > 0){
            return getJobApplicationsByJob(jobId);
        }
        else if(userId > 0){
            return getJobApplicationsByUser(userId);
        }
        else{
            return  getAllJobApplications();
        }
    }

    @Override
    public JobApplication saveJobApplication(JobApplicationRequestDto request) {
        Optional<Job> job = jobRepository.findById(request.getJobId());
        if(job.isEmpty()){
            throw new RuntimeException("Job not found.");
        }

        UserResponseDto user = userService.findById(request.getUserId());
        if(user ==  null){
            throw new RuntimeException("User not found.");
        }

        JobApplication jobApplication = new JobApplication();
        Optional<JobApplication> existingApplication = jobApplicationRepository.findById(request.getId());
        if(existingApplication.isPresent()){
            jobApplication = existingApplication.get();;
        }

        jobApplication.setName(request.getName());
        jobApplication.setJob(job.get());
        jobApplication.setUserId(user.getId());
        jobApplication.setStatus(request.getStatus());
        jobApplication.setSubmittedOn(request.getSubmittedOn());

        return jobApplicationRepository.save(jobApplication);
    }

    private List<JobApplication> getAllJobApplications(){
        return jobApplicationRepository.findAll();
    }

    private List<JobApplication> getJobApplicationsByJob(Long jobId){
        return jobApplicationRepository.findByJobId(jobId);
    }

    private List<JobApplication> getJobApplicationsByUser(Long userId){
        return jobApplicationRepository.findByUserId(userId);
    }

    private List<JobApplication> getJobApplicationsBySearch(Long jobId, Long userId){
        return jobApplicationRepository.findBySearch(jobId, userId);
    }
}
