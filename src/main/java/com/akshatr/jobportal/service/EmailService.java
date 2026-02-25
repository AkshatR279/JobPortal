package com.akshatr.jobportal.service;

import com.akshatr.jobportal.model.utilmodel.Email;
import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendEmail(Email email) throws MessagingException;
}
