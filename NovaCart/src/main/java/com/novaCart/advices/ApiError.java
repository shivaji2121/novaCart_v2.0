package com.novaCart.advices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ApiError {
    private String message;
    private HttpStatus status;
    private List<String> subErrors;
}
