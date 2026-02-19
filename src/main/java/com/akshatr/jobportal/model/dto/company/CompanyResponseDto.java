package com.akshatr.jobportal.model.dto.company;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CompanyResponseDto {
    private Long id;
    private String name;
    private Date createdOn;
    private Date lastUpdatedOn;
}
