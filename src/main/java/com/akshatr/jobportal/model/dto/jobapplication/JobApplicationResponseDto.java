package com.akshatr.jobportal.model.dto.jobapplication;

import com.akshatr.jobportal.model.dto.job.JobResponseShortDto;
import com.akshatr.jobportal.model.dto.referral.ReferralResponseShortDto;
import com.akshatr.jobportal.model.dto.user.UserResponseShortDto;
import com.akshatr.jobportal.model.enums.JobApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class JobApplicationResponseDto implements Serializable {
    private Long id;
    private String name;
    private JobResponseShortDto job;
    private Long userId;
    private JobApplicationStatus status;
    private ReferralResponseShortDto referral;
    private Date submittedOn;
    private Date createdOn;
    private Date lastUpdatedOn;
}
