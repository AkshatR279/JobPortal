package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.GeneralAPIResponse;
import com.akshatr.jobportal.model.dto.user.UserAuthRequest;
import com.akshatr.jobportal.model.dto.user.UserAuthResponse;
import com.akshatr.jobportal.model.dto.user.UserDetailsDTO;
import com.akshatr.jobportal.service.UserAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        ResponseEntity<GeneralAPIResponse> response = restTemplate.exchange(
                "http://userService/api/users/validate",
                HttpMethod.POST,
                new HttpEntity<>(request),
                GeneralAPIResponse.class
        );

        GeneralAPIResponse apiResponse = response.getBody();

        if(apiResponse == null){
            throw new RuntimeException("Failed to authenticate token.");
        }

        if(!apiResponse.getSuccess()){
            throw new JwtException(apiResponse.getMessage());
        }

        UserAuthResponse authResponse = (new ObjectMapper()).convertValue(
                apiResponse.getData(),
                UserAuthResponse.class
        );
        if(authResponse == null){
            throw new RuntimeException("Failed to authenticate token.");
        }

        if(!authResponse.getAuthenticated()){
            throw new JwtException(authResponse.getMessage());
        }

        UserDetailsDTO user = authResponse.getUserDetails();
        if(user == null){
            throw new RuntimeException("Failed to authenticate token.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }
}
