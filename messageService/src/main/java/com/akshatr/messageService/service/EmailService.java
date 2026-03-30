package com.akshatr.messageService.service;

import com.akshatr.messageService.model.utilmodel.Email;
import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendEmail(Email email) throws MessagingException;
}
