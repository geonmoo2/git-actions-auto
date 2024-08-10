package com.example.sl.domain.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}