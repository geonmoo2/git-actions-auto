package com.example.sl.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payment_entity")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private BigDecimal amount;
    private BigDecimal cancelAmount = BigDecimal.ZERO;
    private LocalDateTime paymentDateTime;
    private String paymentMethod;
    private String paymentStatus;
    private String impUid;
    private String merchantUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity bookEntity;

    public PaymentEntity() {}

    public PaymentEntity(BigDecimal amount, LocalDateTime paymentDateTime, String paymentMethod, String paymentStatus, String impUid, String merchantUid, BookEntity bookEntity) {
        this.amount = amount;
        this.paymentDateTime = paymentDateTime;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.impUid = impUid;
        this.merchantUid = merchantUid;
        this.bookEntity = bookEntity;
    }
}
