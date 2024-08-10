package com.example.sl.repository;

import com.example.sl.entity.BookEntity;
import com.example.sl.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByBookEntity(BookEntity bookEntity);
    Optional<PaymentEntity> findByImpUid(String impUid);
}
