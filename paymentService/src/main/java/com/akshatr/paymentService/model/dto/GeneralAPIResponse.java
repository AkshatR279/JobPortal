package com.akshatr.paymentService.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralAPIResponse {
    private Boolean success;
    private String message;
    private int status;
    private Object data;
}
