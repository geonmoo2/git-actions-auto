package com.example.sl.controller;

import com.example.sl.domain.dto.PaymentDto;
import com.example.sl.domain.service.PaymentService;
import com.example.sl.entity.PaymentEntity;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/make")
    @ResponseBody
    public ResponseEntity<?> makePayment(@RequestBody PaymentDto paymentDto) {
        log.info("Received payment request: {}", paymentDto);
        try {
            PaymentEntity paymentEntity = paymentService.makePayment(paymentDto);
            return ResponseEntity.ok(Collections.singletonMap("paymentStatus", "성공"));
        } catch (Exception e) {
            log.error("Payment processing error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("paymentStatus", "실패"));
        }
    }

    @PostMapping("/cancel/{impUid}")
    @ResponseBody
    public ResponseEntity<?> cancelPayment(@PathVariable String impUid, @RequestBody PaymentDto paymentDto) {
        try {
            PaymentEntity cancelledPayment = paymentService.cancelPayment(impUid, paymentDto);
            if ("CANCELLED".equalsIgnoreCase(cancelledPayment.getPaymentStatus())) {
                return ResponseEntity.ok(Collections.singletonMap("paymentStatus", "성공"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("paymentStatus", "실패"));
            }
        } catch (IllegalArgumentException e) {
            log.error("Invalid impUid provided: {}", impUid, e);
            return ResponseEntity.badRequest().body(Collections.singletonMap("success", false));
        } catch (IOException e) {
            log.error("Payment cancellation error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("success", false));
        } catch (IamportResponseException e) {
            log.error("Iamport response error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("success", false));
        }
    }

}