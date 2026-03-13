package com.akshat.userService.util;

import com.akshat.userService.exceptions.BadRequestException;
import com.akshat.userService.model.dto.GeneralAPIResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExceptionHandler {
    private final Map<Class<? extends RuntimeException>, HttpStatus> exceptionMap = new HashMap<>(){
        {
            put(BadRequestException.class, HttpStatus.BAD_REQUEST);
            put(RuntimeException.class, HttpStatus.INTERNAL_SERVER_ERROR);
            put(JwtException.class, HttpStatus.UNAUTHORIZED);
        }
    };

    public ResponseEntity<GeneralAPIResponse> convertExceptionToResponse(RuntimeException ex){
        return new ResponseEntity<GeneralAPIResponse>(
                GeneralAPIResponse.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .status(exceptionMap.get(ex.getClass()).value())
                    .build(),
                exceptionMap.get(ex.getClass())
        );
    }
}
