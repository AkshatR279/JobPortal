package com.akshatr.paymentService.model.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseShortDto {
    private Long id;
    private String name;
}
