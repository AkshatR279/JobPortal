package com.akshatr.jobportal.model.dto.order;

import com.akshatr.jobportal.model.dto.user.UserResponseShortDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponseShortDto {
    private Long id;
    private String name;
}
