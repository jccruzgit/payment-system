package com.payment.orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalPaymentResponse {

    private boolean success;
    private String failureReason;
}
