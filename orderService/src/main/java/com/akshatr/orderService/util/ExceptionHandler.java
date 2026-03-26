package com.akshatr.orderService.util;

import com.akshatr.orderService.exceptions.BadRequestException;
import com.akshatr.orderService.model.dto.GeneralAPIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ExceptionHandler {
    private final Map<Class<? extends RuntimeException>, HttpStatus> exceptionMap = new HashMap<>(){
        {
            put(BadRequestException.class, HttpStatus.BAD_REQUEST);
            put(RuntimeException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

    public ResponseEntity<GeneralAPIResponse> convertExceptionToResponse(RuntimeException ex){
        RuntimeException e = ex;
        if(!exceptionMap.containsKey(e.getClass())){
            e = new RuntimeException(ex.getMessage());
        }

        return new ResponseEntity<GeneralAPIResponse>(
                GeneralAPIResponse.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .status(exceptionMap.get(e.getClass()).value())
                    .build(),
                exceptionMap.get(e.getClass())
        );
    }

    public void convertExceptionToResponse(RuntimeException ex, HttpServletResponse response) throws IOException {
        RuntimeException e = ex;
        if(!exceptionMap.containsKey(e.getClass())){
            e = new RuntimeException(ex.getMessage());
        }

        response.setStatus(exceptionMap.get(e.getClass()).value());
        response.setContentType("application/json");

        GeneralAPIResponse errorResponse = GeneralAPIResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .status(exceptionMap.get(e.getClass()).value())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(errorResponse);

        response.getWriter().write(json);
        response.getWriter().flush();
    }
}
