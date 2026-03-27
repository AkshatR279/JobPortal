package com.akshatr.paymentService.model.utilmodel;

import lombok.Data;

@Data
public class Credentials {
    private String key;
    private String secret;
    private String webhookSecret;

    public Credentials(String key, String secret, String webhookSecret){
        this.key = key;
        this.secret = secret;
        this.webhookSecret = webhookSecret;
    }
}
