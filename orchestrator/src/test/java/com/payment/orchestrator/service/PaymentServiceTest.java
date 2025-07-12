package com.payment.orchestrator.service;

import com.payment.orchestrator.client.ExternalPaymentClient;
import com.payment.orchestrator.dto.ExternalPaymentResponse;
import com.payment.orchestrator.dto.PaymentRequest;
import com.payment.orchestrator.dto.PaymentResponse;
import com.payment.orchestrator.entity.PaymentStatus;
import com.payment.orchestrator.entity.PaymentTransaction;
import com.payment.orchestrator.repository.PaymentTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentServiceTest {

    private PaymentService service;
    private PaymentTransactionRepository repository;
    private ExternalPaymentClient client;

    @BeforeEach
    void setUp() {
        repository = mock(PaymentTransactionRepository.class);
        client = mock(ExternalPaymentClient.class);
        service = new PaymentService(repository, client);
    }

    @Test
    void shouldProcessSuccessfulPayment() {
        PaymentRequest request = new PaymentRequest();
        request.setCustomerId(1L);
        request.setAmount(new BigDecimal("500"));
        request.setPaymentMethod("credit_card");

        when(client.applyPayment(any())).thenReturn(new ExternalPaymentResponse(true, null));
        when(repository.save(any(PaymentTransaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaymentResponse response = service.orchestratePayment(request);

        assertEquals(PaymentStatus.SUCCESS, response.getStatus());
        assertEquals("Payment processed successfully", response.getMessage());
    }

    @Test
    void shouldProcessFailedPayment() {
        PaymentRequest request = new PaymentRequest();
        request.setCustomerId(1L);
        request.setAmount(new BigDecimal("1500"));
        request.setPaymentMethod("credit_card");

        when(client.applyPayment(any()))
                .thenReturn(new ExternalPaymentResponse(false, "Amount exceeds limits"));
        when(repository.save(any(PaymentTransaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaymentResponse response = service.orchestratePayment(request);

        assertEquals(PaymentStatus.FAILURE, response.getStatus());
        assertTrue(response.getMessage().contains("Amount exceeds limits"));
    }

    @Test
    void shouldHandleExceptionWhenExternalServiceFails() {
        PaymentRequest request = new PaymentRequest();
        request.setCustomerId(1L);
        request.setAmount(new BigDecimal("500"));
        request.setPaymentMethod("paypal");

        when(client.applyPayment(any()))
                .thenThrow(new RuntimeException("External payment service is not available"));
        when(repository.save(any(PaymentTransaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaymentResponse response = service.orchestratePayment(request);

        assertEquals(PaymentStatus.FAILURE, response.getStatus());
        assertTrue(response.getMessage().contains("External payment service is not available"));
    }
}
