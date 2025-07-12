package com.payment.orchestrator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String paymentMethod;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String failureReason;
}
