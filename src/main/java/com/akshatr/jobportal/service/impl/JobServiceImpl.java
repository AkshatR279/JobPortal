package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.job.JobRequestDto;
import com.akshatr.jobportal.model.entity.Company;
import com.akshatr.jobportal.model.entity.Job;
import com.akshatr.jobportal.repository.CompanyRepository;
import com.akshatr.jobportal.repository.JobRepository;
import com.akshatr.jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;

    @Override
    public List<Job> getJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job saveJob(JobRequestDto request) {
        Job job = new Job();
        Optional<Job> existingJob = jobRepository.findById(request.getId());
        if(existingJob.isPresent()){
            job = existingJob.get();
        }

        Optional<Company> company = companyRepository.findById(request.getCompanyId());
        if(company.isEmpty()){
            throw new RuntimeException("Company not found.");
        }

        job.setName(request.getName());
        job.setIdentifier(request.getIdentifier());
        job.setExperienceMin(request.getExperienceMin());
        job.setExperienceMax(request.getExperienceMax());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setCompany(company.get());
        job.setPostedOn(request.getPostedOn());
        job.setExpiredOn(request.getExpiredOn());
        job.setStatus(request.getStatus());

        return jobRepository.save(job);
    }


}
