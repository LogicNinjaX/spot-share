package com.example.spot_share.controller.exception_handler;

import com.example.spot_share.util.api_response.ErrorResponse;
import com.example.spot_share.util.exception.BookingException;
import com.example.spot_share.util.exception.ParkingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParkingException.class)
    public ResponseEntity<com.example.spot_share.util.api_response.ErrorResponse<?>> parkingExceptionHandler(ParkingException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(false, "error occurred", HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<com.example.spot_share.util.api_response.ErrorResponse<?>> bookingExceptionHandler(BookingException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(false, "error occurred", HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<?>> validationExceptionHandler(MethodArgumentNotValidException ex){

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(false, "invalid request", HttpStatus.BAD_REQUEST.value(), errors));
    }

}
