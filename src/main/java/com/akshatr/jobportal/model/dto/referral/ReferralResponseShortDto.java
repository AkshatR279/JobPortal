package com.akshatr.jobportal.model.dto.referral;

import com.akshatr.jobportal.model.dto.user.UserResponseShortDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReferralResponseShortDto {
    private Long id;
    private String message;
    private UserResponseShortDto user;
}
