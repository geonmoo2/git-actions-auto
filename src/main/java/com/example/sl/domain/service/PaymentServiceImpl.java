package com.example.sl.domain.service;

import com.example.sl.domain.dto.PaymentDto;
import com.example.sl.entity.BookEntity;
import com.example.sl.entity.PaymentEntity;
import com.example.sl.repository.BookRepository;
import com.example.sl.repository.PaymentRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookRepository bookRepository;

    private final IamportClient iamportClient;

    @Autowired
    public PaymentServiceImpl(@Value("${imp.api.key}") String apiKey, @Value("${imp.api.secret}") String apiSecret) {
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    @Override
    public PaymentEntity makePayment(PaymentDto paymentDto) throws IamportResponseException, IOException {
        // BookEntity를 bookId로 조회하여 설정
        BookEntity bookEntity = bookRepository.findById(paymentDto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid bookId"));

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(paymentDto.getAmount());
        paymentEntity.setPaymentDateTime(LocalDateTime.now());
        paymentEntity.setPaymentMethod(paymentDto.getPaymentMethod());
        paymentEntity.setPaymentStatus("PENDING");
        paymentEntity.setImpUid(paymentDto.getImpUid());
        paymentEntity.setMerchantUid(paymentDto.getMerchantUid());
        paymentEntity.setBookEntity(bookEntity); // BookEntity 설정

        paymentEntity = paymentRepository.save(paymentEntity);

        // 결제 상태 확인
        IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(paymentDto.getImpUid());
        if (paymentResponse.getResponse() != null) {
            log.info("Payment status after request: {}", paymentResponse.getResponse().getStatus());
            paymentEntity.setPaymentStatus(paymentResponse.getResponse().getStatus());
        }

        return paymentRepository.save(paymentEntity);
    }

    @Override
    public PaymentEntity cancelPayment(String impUid, PaymentDto paymentDto) throws IamportResponseException, IOException {
        log.info("Attempting to cancel payment with impUid: {}", impUid);
        Optional<PaymentEntity> paymentEntityOptional = paymentRepository.findByImpUid(impUid);
        if (!paymentEntityOptional.isPresent()) {
            log.error("Invalid impUid: {}", impUid);
            throw new IllegalArgumentException("Invalid impUid");
        }

        PaymentEntity paymentEntity = paymentEntityOptional.get();

        // 이미 취소된 주문인지 확인
        if ("CANCELLED".equalsIgnoreCase(paymentEntity.getPaymentStatus())) {
            log.warn("이미 전액취소된 주문입니다: {}", impUid);
            return paymentEntity;
        }

        BigDecimal cancelRequestAmount = paymentDto.getCancelRequestAmount();
        if (cancelRequestAmount == null) {
            cancelRequestAmount = paymentEntity.getAmount();
        }

        CancelData cancelData = new CancelData(paymentEntity.getImpUid(), true, cancelRequestAmount);
        cancelData.setReason(paymentDto.getReason());

        if (paymentDto.getRefundHolder() != null) {
            cancelData.setRefund_holder(paymentDto.getRefundHolder());
            cancelData.setRefund_bank(paymentDto.getRefundBank());
            cancelData.setRefund_account(paymentDto.getRefundAccount());
        }

        try {
            IamportResponse<Payment> paymentResponse = iamportClient.cancelPaymentByImpUid(cancelData);
            log.info("Requesting payment cancellation from Iamport: {}", cancelData);

            if (paymentResponse.getResponse() == null) {
                log.error("Payment cancellation failed: {}", paymentResponse.getMessage());
                throw new IOException("Payment cancellation failed: " + paymentResponse.getMessage());
            }

            log.info("Payment cancellation response: {}", paymentResponse.getResponse());

            paymentEntity.setPaymentStatus("CANCELLED");
            paymentEntity.setCancelAmount(cancelRequestAmount);

        } catch (IamportResponseException e) {
            if (e.getMessage().contains("이미 전액취소된 주문입니다.")) {
                log.warn("이미 전액취소된 주문입니다: {}", impUid);
                paymentEntity.setPaymentStatus("CANCELLED");
            } else {
                throw e;
            }
        }

        // 최종 상태 확인
        IamportResponse<Payment> finalPaymentResponse = iamportClient.paymentByImpUid(impUid);
        if (finalPaymentResponse.getResponse() != null) {
            log.info("Final payment status: {}", finalPaymentResponse.getResponse().getStatus());
            paymentEntity.setPaymentStatus(finalPaymentResponse.getResponse().getStatus());
        }

        return paymentRepository.save(paymentEntity);
    }
}
