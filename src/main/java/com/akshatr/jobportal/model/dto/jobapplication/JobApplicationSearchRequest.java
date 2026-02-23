package com.akshatr.jobportal.model.dto.jobapplication;

import lombok.Data;

@Data
public class JobApplicationSearchRequest {
    private Long jobId;
    private Long userId;
}
