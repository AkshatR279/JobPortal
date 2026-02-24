package com.akshatr.jobportal.model.dto.jobapplication;

import com.akshatr.jobportal.model.dto.job.JobResponseShortDto;
import com.akshatr.jobportal.model.dto.user.UserResponseShortDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobApplicationResponseShortDto {
    private Long id;
    private UserResponseShortDto user;
    private JobResponseShortDto job;
}
