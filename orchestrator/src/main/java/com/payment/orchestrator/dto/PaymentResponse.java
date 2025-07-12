package com.payment.orchestrator.dto;

import com.payment.orchestrator.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {

    private PaymentStatus status;
    private String message;
}
