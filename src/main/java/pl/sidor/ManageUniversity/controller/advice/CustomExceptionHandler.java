package pl.sidor.ManageUniversity.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.sidor.ManageUniversity.exception.UniversityException;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UniversityException.class)
    public ResponseEntity<CustomErrorResponse> findById(Exception ex) {

        CustomErrorResponse customErrorResponse = new CustomErrorResponse();

        customErrorResponse.setLocalDateTime(LocalDateTime.now());
        customErrorResponse.setErrorMessage(ex.getMessage());
        customErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }
}

