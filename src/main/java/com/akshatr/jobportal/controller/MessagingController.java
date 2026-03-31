package com.akshatr.jobportal.controller;

import com.akshatr.jobportal.model.dto.GeneralAPIResponse;
import com.akshatr.jobportal.model.utilmodel.Email;
import com.akshatr.jobportal.service.EmailService;
import com.akshatr.jobportal.util.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messaging")
@RequiredArgsConstructor
public class MessagingController {
    private final EmailService emailService;
    private final ExceptionHandler exceptionHandler;

    @PostMapping("/email/send")
    public ResponseEntity<GeneralAPIResponse> sendEmail(@RequestBody Email email){
        try{
            emailService.sendEmail(email);

            return ResponseEntity.ok(
                    GeneralAPIResponse.builder()
                            .success(true)
                            .message("Success")
                            .status(HttpStatus.OK.value())
                            .build()
            );
        } catch (RuntimeException ex) {
            return exceptionHandler.convertExceptionToResponse(ex);
        }
    }
}
