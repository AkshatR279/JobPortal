package com.akshatr.jobportal.model.dto.referral;

import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationResponseShortDto;
import com.akshatr.jobportal.model.dto.user.UserResponseShortDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReferralResponseDto {
    private Long id;
    private String name;
    private String message;
    private UserResponseShortDto user;
    private JobApplicationResponseShortDto application;
    private Date createdOn;
    private Date lastUpdatedOn;
}
