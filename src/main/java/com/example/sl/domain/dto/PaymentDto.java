package com.example.sl.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private Long bookId;
    private BigDecimal amount;
    private LocalDateTime paymentDateTime;
    private String paymentMethod;
    private String paymentStatus;
    private String impUid;
    private String merchantUid;

    // 환불 관련 필드
    private BigDecimal cancelRequestAmount;
    private String reason;
    private String refundHolder;
    private String refundBank;
    private String refundAccount;
}
