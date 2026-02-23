package com.akshatr.jobportal.repository;

import com.akshatr.jobportal.model.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
    public List<JobApplication> findByJobId(Long jobId);
    public List<JobApplication> findByUserId(Long userId);

    @Query("SELECT J FROM JobApplication J WHERE J.job.id = :jobId AND J.user.id = :userId")
    public List<JobApplication> findBySearch(@Param("jobId") Long jobId, @Param("userId") Long userId);
}
