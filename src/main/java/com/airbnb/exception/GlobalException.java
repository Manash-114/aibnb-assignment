package com.airbnb.exception;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public ProblemDetail loginExceptionHandler(Exception e){
        ProblemDetail errorDetails = ProblemDetail.forStatusAndDetail(HttpStatus.OK,e.getMessage());
        errorDetails.setProperty("message",e.getLocalizedMessage());
        return  errorDetails;
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ProblemDetail invalidJwt(Exception e){
        ProblemDetail errorDetails = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,e.getMessage());
        errorDetails.setProperty("message",e.getLocalizedMessage());
        return  errorDetails;
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ProblemDetail malformedJwtExceptionHandler(Exception e){
        ProblemDetail errorDetails = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,e.getMessage());
        errorDetails.setProperty("message","invalid jwt Token");
        return  errorDetails;
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail expiredJwtExceptionHandler(Exception e){
        ProblemDetail errorDetails = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,e.getMessage());
        errorDetails.setProperty("message","jwt-token-expired");
        return  errorDetails;
    }

    @ExceptionHandler(SignatureException.class)
    public ProblemDetail signatureJwtExceptionHandler(Exception e){
        ProblemDetail errorDetails = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,e.getMessage());
        errorDetails.setProperty("message","invalid jwt signature");
        return  errorDetails;
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public ProblemDetail accessDeniedException(CustomUnauthorizedException e){
        ProblemDetail errorDetails = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,e.getMessage());
        errorDetails.setProperty("message","Access-Denied. Unauthorized access attempt detected");
        return  errorDetails;
    }
    @ExceptionHandler(ResourceNotFoundExcaption.class)
    public ProblemDetail resourceNotFound(ResourceNotFoundExcaption e){
        ProblemDetail errorDetails = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,e.getMessage());
        errorDetails.setProperty("message",e.getLocalizedMessage());
        return  errorDetails;
    }

    @ExceptionHandler(NotAllowedException.class)
    public ProblemDetail notAllowed(Exception e){
        ProblemDetail errorDetails = ProblemDetail.forStatusAndDetail(HttpStatus.OK,e.getMessage());
        errorDetails.setProperty("message",e.getLocalizedMessage());
        return  errorDetails;
    }

}
