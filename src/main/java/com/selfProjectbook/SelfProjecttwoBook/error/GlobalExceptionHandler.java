package com.selfProjectbook.SelfProjecttwoBook.error;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNameNotFoundException(
            UsernameNotFoundException ex, HttpServletRequest request){
        ApiError apiError = new ApiError("User name not found"
                +ex.getMessage(), HttpStatus.NOT_FOUND, request.getRequestURI());
        return new ResponseEntity<>(apiError,apiError.getStatusCode());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException
            (AuthenticationException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError("Authentication failed: "
                + ex.getMessage(), HttpStatus.UNAUTHORIZED, request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException
            (JwtException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError("Invalid JWT token: "
                + ex.getMessage(), HttpStatus.UNAUTHORIZED, request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException
            (AccessDeniedException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError("Access denied: Insufficient permissions",
                HttpStatus.FORBIDDEN, request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException
            (Exception ex, HttpServletRequest request) {
        ApiError apiError = new ApiError("An unexpected error occurred: "
                + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
