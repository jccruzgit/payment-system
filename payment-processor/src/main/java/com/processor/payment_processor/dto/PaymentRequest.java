package com.processor.payment_processor.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotNull
    private Long customerId;

    @NotNull
    @DecimalMin(value = "0.01")
    private Long amount;

    @NotBlank
    private String paymentMethod;
}
