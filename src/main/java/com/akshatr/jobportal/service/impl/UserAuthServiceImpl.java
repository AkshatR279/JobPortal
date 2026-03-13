package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.user.UserAuthRequest;
import com.akshatr.jobportal.model.dto.user.UserAuthResponse;
import com.akshatr.jobportal.service.UserAuthService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
    private final RestTemplate restTemplate;

    @Override
    public UserDetails validateToken(String token) {
        UserAuthRequest request = new UserAuthRequest();
        request.setToken(token);

        UserAuthResponse authResponse = restTemplate.postForObject(
                "http://userService/v1/users/validate",
                request,
                UserAuthResponse.class
        );

        if(authResponse==null){
            throw new RuntimeException("Failed to authenticate token.");
        }

        if(!authResponse.getAuthenticated()){
            throw new JwtException(authResponse.getMessage());
        }

        return authResponse.getUserDetails();
    }
}
