package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.utilmodel.Email;
import com.akshatr.jobportal.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public void send(@RequestBody Email request){
        try{
            emailService.sendEmail(request);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
