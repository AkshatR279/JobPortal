package com.akshatr.jobportal.model.dto.job;

import com.akshatr.jobportal.model.enums.JobStatus;
import lombok.Data;

import java.util.Date;

@Data
public class JobRequestDto {
    private Long id;
    private String name;
    private String identifier;
    private Long salaryMin;
    private Long salaryMax;
    private int experienceMin;
    private int experienceMax;
    private Date postedOn;
    private Date expiredOn;
    private JobStatus status;
    private Long companyId;
}
