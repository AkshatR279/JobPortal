package com.akshatr.jobportal.model.dto.jobapplication;

import com.akshatr.jobportal.model.dto.job.JobResponseShortDto;
import com.akshatr.jobportal.model.dto.user.UserResponseShortDto;
import com.akshatr.jobportal.model.entity.Job;
import com.akshatr.jobportal.model.entity.User;
import com.akshatr.jobportal.model.enums.JobApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class JobApplicationResponseDto {
    private Long id;
    private String name;
    private JobResponseShortDto job;
    private UserResponseShortDto user;
    private JobApplicationStatus status;
    private Date submittedOn;
    private Date createdOn;
    private Date lastUpdatedOn;
}
