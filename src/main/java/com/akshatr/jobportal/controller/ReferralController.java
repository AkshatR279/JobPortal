package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.dto.referral.ReferralRequestDto;
import com.akshatr.jobportal.model.dto.referral.ReferralResponseDto;
import com.akshatr.jobportal.model.entity.Referral;
import com.akshatr.jobportal.service.ReferralService;
import com.akshatr.jobportal.util.DtoConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/referrals")
@RequiredArgsConstructor
public class ReferralController {
    private final ReferralService referralService;
    private final DtoConvertor dtoConvertor;

    @PostMapping("/refer")
    public ReferralResponseDto refer(@RequestBody ReferralRequestDto request){
        return convertReferralToResponseDto(referralService.refer(request));
    }

    private ReferralResponseDto convertReferralToResponseDto(Referral referral){
        return ReferralResponseDto.builder()
                .id(referral.getId())
                .name(referral.getName())
                .message(referral.getMessage())
                .createdOn(referral.getCreatedOn())
                .lastUpdatedOn(referral.getLastUpdatedOn())
                .application(dtoConvertor.convertToJobApplicationShort(referral.getReferralFor()))
                .user(dtoConvertor.convertToUserShort(referral.getUser()))
                .build();
    }
}
