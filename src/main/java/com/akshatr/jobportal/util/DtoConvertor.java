package com.akshatr.jobportal.util;

import com.akshatr.jobportal.model.dto.job.JobResponseShortDto;
import com.akshatr.jobportal.model.dto.jobapplication.JobApplicationResponseShortDto;
import com.akshatr.jobportal.model.dto.referral.ReferralResponseShortDto;
import com.akshatr.jobportal.model.dto.user.UserResponseShortDto;
import com.akshatr.jobportal.model.entity.Job;
import com.akshatr.jobportal.model.entity.JobApplication;
import com.akshatr.jobportal.model.entity.Referral;
import com.akshatr.jobportal.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DtoConvertor {
    public JobResponseShortDto convertToJobShort(Job job){
        return JobResponseShortDto.builder()
                .id(job.getId())
                .name(job.getName())
                .build();
    }

    public UserResponseShortDto convertToUserShort(User user){
        return UserResponseShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public JobApplicationResponseShortDto convertToJobApplicationShort(JobApplication jobApplication){
        return JobApplicationResponseShortDto.builder()
                .id(jobApplication.getId())
                .job(convertToJobShort(jobApplication.getJob()))
                .user(convertToUserShort(jobApplication.getUser()))
                .build();
    }

    public ReferralResponseShortDto convertReferralToResponseShort(Referral referral){
        return ReferralResponseShortDto.builder()
                .id(referral.getId())
                .message(referral.getMessage())
                .user(convertToUserShort(referral.getUser()))
                .build();
    }
}
