package com.akshatr.messageService.messaging;

import com.akshatr.messageService.model.utilmodel.Email;
import com.akshatr.messageService.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final EmailService emailService;

    @KafkaListener(topics = "SEND_EMAIL")
    public void emailListener(Email email){
        try {
            emailService.sendEmail(email);
        }
        catch (MessagingException ignored){

        }
    }
}
