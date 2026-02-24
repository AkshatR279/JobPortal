package com.akshatr.jobportal.model.dto.jobapplication;

import com.akshatr.jobportal.model.enums.JobApplicationStatus;
import lombok.Data;

import java.util.Date;

@Data
public class JobApplicationRequestDto {
    private Long id;
    private String name;
    private Long jobId;
    private Long userId;
    private JobApplicationStatus status;
    private Date submittedOn;
}
