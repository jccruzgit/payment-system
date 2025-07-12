package com.payment.orchestrator.client;

import com.payment.orchestrator.dto.ExternalPaymentResponse;
import com.payment.orchestrator.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "external-payment", url = "${external.payment.url}")
public interface ExternalPaymentClient {

    @PostMapping("/apply-payment")
    ExternalPaymentResponse applyPayment(PaymentRequest request);
}
