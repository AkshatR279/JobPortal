package com.akshatr.jobportal.model.dto.referral;

import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationResponseShortDto;
import com.akshatr.jobportal.model.dto.user.UserResponseShortDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReferralResponseShortDto {
    private Long id;
    private String message;
    private UserResponseShortDto user;
}
