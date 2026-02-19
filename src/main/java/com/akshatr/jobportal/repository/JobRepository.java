package com.akshatr.jobportal.repository;

import com.akshatr.jobportal.model.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
