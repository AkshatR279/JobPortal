package com.akshatr.jobportal.model.dto.referral;

import lombok.Data;

@Data
public class ReferralRequestDto {
    private Long id;
    private String name;
    private String message;
    private Long jobApplicationId;
    private Long userId;
}
