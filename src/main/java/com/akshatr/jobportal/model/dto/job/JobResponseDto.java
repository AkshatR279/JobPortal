package com.akshatr.jobportal.model.dto.job;

import com.akshatr.jobportal.model.enums.JobStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class JobResponseDto {
    private Long id;
    private String name;
    private Date createdOn;
    private Date lastUpdatedOn;
    private String identifier;
    private Long salaryMin;
    private Long salaryMax;
    private int experienceMin;
    private int experienceMax;
    private Date postedOn;
    private Date expiredOn;
    private JobStatus status;
}
