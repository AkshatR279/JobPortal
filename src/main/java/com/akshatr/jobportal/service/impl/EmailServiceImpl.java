package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.utilmodel.Email;
import com.akshatr.jobportal.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.internals.Topic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final KafkaTemplate<String,Object> kafka;

    @Override
    public void sendEmail(Email email)  {
        kafka.send("SEND_EMAIL", email);
    }
}
