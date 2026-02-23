package com.akshatr.jobportal.model.dto.job;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobResponseShortDto {
    private Long id;
    private String name;
}
