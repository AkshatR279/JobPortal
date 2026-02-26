package com.akshatr.jobportal.model.utilmodel;

import lombok.Data;

@Data
public class Credentials {
    private String key;
    private String secret;

    public Credentials(String key, String secret){
        this.key = key;
        this.secret = secret;
    }
}
