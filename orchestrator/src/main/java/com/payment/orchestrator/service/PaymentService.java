package com.payment.orchestrator.service;

import com.payment.orchestrator.client.ExternalPaymentClient;
import com.payment.orchestrator.dto.ExternalPaymentResponse;
import com.payment.orchestrator.dto.PaymentRequest;
import com.payment.orchestrator.dto.PaymentResponse;
import com.payment.orchestrator.entity.PaymentStatus;
import com.payment.orchestrator.entity.PaymentTransaction;
import com.payment.orchestrator.repository.PaymentTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentTransactionRepository repository;
    private final ExternalPaymentClient paymentClient;

    public PaymentService(PaymentTransactionRepository repository, ExternalPaymentClient paymentClient) {
        this.repository = repository;
        this.paymentClient = paymentClient;
    }

    public PaymentResponse orchestratePayment(PaymentRequest request) {
        PaymentTransaction tx = new PaymentTransaction();
        tx.setCustomerId(request.getCustomerId());
        tx.setAmount(request.getAmount());
        tx.setPaymentMethod(request.getPaymentMethod());
        tx.setCreatedAt(LocalDateTime.now());

        try {
            ExternalPaymentResponse response = paymentClient.applyPayment(request);
            if (response.isSuccess()) {
                tx.setStatus(PaymentStatus.SUCCESS);
                tx.setFailureReason(null);
            } else {
                tx.setStatus(PaymentStatus.FAILURE);
                tx.setFailureReason(response.getFailureReason());
            }
        } catch (Exception e) {
            tx.setStatus(PaymentStatus.FAILURE);
            tx.setFailureReason("External payment service is not available");
        }

        repository.save(tx);

        return new PaymentResponse(tx.getStatus(), tx.getStatus().equals(PaymentStatus.SUCCESS) ? "Payment processed successfully"
                : "Payment failed: " + tx.getFailureReason());
    }


}
