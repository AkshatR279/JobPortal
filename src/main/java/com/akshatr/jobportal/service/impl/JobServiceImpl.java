package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.job.JobRequestDto;
import com.akshatr.jobportal.model.entity.Company;
import com.akshatr.jobportal.model.entity.Job;
import com.akshatr.jobportal.repository.CompanyRepository;
import com.akshatr.jobportal.repository.JobRepository;
import com.akshatr.jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final CacheManager cacheManager;

    @Override
    public List<Job> getJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job getJob(Long id) {
        Cache cache  = cacheManager.getCache("JOB_PORTAL_JOBS");
        if(cache != null && cache.get(id) != null){
            return (Job) cache.get(id);
        }

        Optional<Job> job = jobRepository.findById(id);
        if(job.isEmpty()){
            throw new RuntimeException("Job not found.");
        }

        return job.get();
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

        jobRepository.save(job);

        Cache cache = cacheManager.getCache("JOB_PORTAL_JOBS");
        if(cache != null)
            cache.put(job.getId(), job);

        return job;
    }


}
