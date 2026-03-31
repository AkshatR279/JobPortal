package com.akshatr.jobportal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralAPIResponse {
    private Boolean success;
    private String message;
    private int status;
    private Object data;
}
