package pl.sidor.ManageUniversity.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
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

@Getter
@Setter
class CustomErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd hh:mm:ss")
    private LocalDateTime localDateTime;
    private int status;
    private String errorMessage;
}
