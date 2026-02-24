package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.dto.referral.ReferralRequestDto;
import com.akshatr.jobportal.model.entity.Referral;

public interface ReferralService {
    public Referral refer(ReferralRequestDto request);
}
