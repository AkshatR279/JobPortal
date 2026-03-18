package com.akshatr.jobportal.model.dto.job;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class JobResponseShortDto implements Serializable {
    private Long id;
    private String name;
}
