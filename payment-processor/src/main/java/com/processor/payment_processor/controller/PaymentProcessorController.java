package com.processor.payment_processor.controller;

import com.processor.payment_processor.dto.ExternalPaymentResponse;
import com.processor.payment_processor.dto.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentProcessorController {

    @PostMapping("/apply-payment")
    public ResponseEntity<ExternalPaymentResponse> applyPayment(@RequestBody PaymentRequest request) {
        if(request.getAmount().doubleValue() > 1000) {
            return ResponseEntity.ok(new ExternalPaymentResponse(false, "Amount exceeds limit"));
         }

        return ResponseEntity.ok(new ExternalPaymentResponse(true, null));
    }
}
