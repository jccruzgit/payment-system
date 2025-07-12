package com.payment.orchestrator.controller;

import com.payment.orchestrator.dto.PaymentRequest;
import com.payment.orchestrator.dto.PaymentResponse;
import com.payment.orchestrator.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @Operation(summary = "Procesa un pago", description = "Recibe los datos de pago y los orquesta con el servicio externo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado del procesamiento del pago"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = service.orchestratePayment(request);
        return ResponseEntity.ok(response);
    }
}
