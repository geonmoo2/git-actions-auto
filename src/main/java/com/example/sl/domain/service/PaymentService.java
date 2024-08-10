package com.example.sl.domain.service;

import com.example.sl.domain.dto.PaymentDto;
import com.example.sl.entity.PaymentEntity;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface PaymentService {
    PaymentEntity makePayment(PaymentDto paymentDto) throws IamportResponseException, IOException;
    PaymentEntity cancelPayment(String impUid, PaymentDto paymentDto) throws IamportResponseException, IOException;
}
