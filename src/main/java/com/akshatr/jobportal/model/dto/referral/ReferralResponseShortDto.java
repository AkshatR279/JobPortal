package com.akshatr.jobportal.model.dto.referral;

import com.akshatr.jobportal.model.dto.user.UserResponseShortDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ReferralResponseShortDto implements Serializable {
    private Long id;
    private String message;
    private Long userId;
}
