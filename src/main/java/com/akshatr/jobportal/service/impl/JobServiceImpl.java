package com.akshatr.jobportal.service.impl;

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
}
