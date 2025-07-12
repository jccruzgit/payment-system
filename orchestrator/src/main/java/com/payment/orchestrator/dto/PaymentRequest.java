package com.payment.orchestrator.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    @NotNull(message = "Customer ID is required") //Valida que el customerId no sea null
    private Long customerId;


    @NotNull(message = "Amount is required") //Valida que el monto no sea null
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0.01") //Valida que el monto sea mayor a 0.01
    @DecimalMax(value = "1000.00", message = "Amount must be less than 1000.00") //Valida que el monto sea menor a 1000.00
    private BigDecimal amount;

    @NotBlank(message = "Payment method is required") // Valida que el metodo de pago no sea null
    private String paymentMethod;
}
